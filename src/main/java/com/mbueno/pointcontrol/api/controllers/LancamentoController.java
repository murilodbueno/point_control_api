package com.mbueno.pointcontrol.api.controllers;

import com.mbueno.pointcontrol.api.dtos.LancamentoDto;
import com.mbueno.pointcontrol.api.entities.Funcionario;
import com.mbueno.pointcontrol.api.entities.Lancamento;
import com.mbueno.pointcontrol.api.enums.TipoEnum;
import com.mbueno.pointcontrol.api.response.Response;
import com.mbueno.pointcontrol.api.services.FuncionarioService;
import com.mbueno.pointcontrol.api.services.LancamentoService;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

@RestController
@RequestMapping("/api/lancamentos")
@CrossOrigin(origins = "*")
public class LancamentoController {

    private static final Logger log = LoggerFactory.getLogger(LancamentoController.class);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private FuncionarioService funcionarioService;

    @Value("${paginacao.qtd_por_pagina}")
    private int qtdPorPagina;

    public LancamentoController() {
    }

    /**
     * Retorna a listagem de lancamentos de um funcionario
     *
     * @param funcionarioId
     * @param pag
     * @param ord
     * @param dir
     * @return ResponseEntity<Response<Page<LancamentoDto>>>
     */
    @GetMapping(value = "/funcionario/{funcionarioId}")
    public ResponseEntity<Response<Page<LancamentoDto>>> listarPorFuncionarioId(
            @PathVariable("funcionarioId") Long funcionarioId,
            @RequestParam(value = "pag", defaultValue = "0") int pag,
            @RequestParam(value = "ord", defaultValue = "id") String ord,
            @RequestParam(value = "dir", defaultValue = "DESC") String dir){
        log.info("Buscando Lançamentos por ID do funcionario: {}, pagina: {}", funcionarioId, pag );
        Response<Page<LancamentoDto>> response = new Response<>();
        PageRequest pageRequest = new PageRequest(pag, qtdPorPagina, Sort.Direction.valueOf(dir), ord);
        Page<Lancamento> lancamentos = lancamentoService.buscarPorIdFuncionario(funcionarioId, pageRequest);
        Page<LancamentoDto> lancamentoDtos = lancamentos.map(lancamento -> converterLancamentoDto(lancamento));
        response.setData(lancamentoDtos);
        return ResponseEntity.ok(response);
    }

    /**
     * Retorna um lancamento por ID
     * @param id
     * @return ResponseEntity<Response<LancamentoDto>>
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<LancamentoDto>> listarPorId(@PathVariable("id") Long id){
        log.info("Buscando lancamento por id: {}", id);
        Response<LancamentoDto> response = new Response<>();
        Optional<Lancamento> lancamento = lancamentoService.buscarPorId(id);

        if(!lancamento.isPresent()){
            log.info("Lancamento não encontrado para o ID: {}", id);
            response.getErrors().add("Lancamento não encontrado para o ID " + id);
            return ResponseEntity.badRequest().body(response);
        }
        response.setData(converterLancamentoDto(lancamento.get()));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<LancamentoDto>> adicionar(@Valid @RequestBody LancamentoDto lancamentoDto,
                                                             BindingResult result) throws ParseException {
        log.info("Adicionando um lancamento: {}", lancamentoDto.toString());
        Response<LancamentoDto> response = new Response<>();
        validarFuncionario(lancamentoDto, result);
        Lancamento lancamento = lancamentoService.persistir(convertDtoParaLancamento(lancamentoDto, result));

        if(result.hasErrors()){
            result.getAllErrors().forEach(erro -> response.getErrors().add(erro.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        lancamento = lancamentoService.persistir(lancamento);
        response.setData(converterLancamentoDto(lancamento));
        return ResponseEntity.ok(response);
    }

    /**
     * Atualiza os dados de um lançamento.
     *
     * @param id
     * @param lancamentoDto
     * @return ResponseEntity<Response<Lancamento>>
     * @throws ParseException
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<LancamentoDto>> atualizar(@PathVariable("id") Long id,
                                                             @Valid @RequestBody LancamentoDto lancamentoDto, BindingResult result) throws ParseException {
        log.info("Atualizando lançamento: {}", lancamentoDto.toString());
        Response<LancamentoDto> response = new Response<LancamentoDto>();
        validarFuncionario(lancamentoDto, result);
        lancamentoDto.setId(Optional.of(id));
        Lancamento lancamento = convertDtoParaLancamento(lancamentoDto, result);

        if (result.hasErrors()) {
            log.error("Erro validando lançamento: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        lancamento = this.lancamentoService.persistir(lancamento);
        response.setData(this.converterLancamentoDto(lancamento));
        return ResponseEntity.ok(response);
    }

    /**
     * Remove um lançamento por ID.
     *
     * @param id
     * @return ResponseEntity<Response<Lancamento>>
     */
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
        log.info("Removendo lançamento: {}", id);
        Response<String> response = new Response<String>();
        Optional<Lancamento> lancamento = this.lancamentoService.buscarPorId(id);

        if (!lancamento.isPresent()) {
            log.info("Erro ao remover devido ao lançamento ID: {} ser inválido.", id);
            response.getErrors().add("Erro ao remover lançamento. Registro não encontrado para o id " + id);
            return ResponseEntity.badRequest().body(response);
        }

        this.lancamentoService.remover(id);
        return ResponseEntity.ok(new Response<String>());
    }

    /**
     * Converte uma entidade lancamento para DTO
     *
     * @param lancamento
     * @return LancamentoDto
     */
    private LancamentoDto converterLancamentoDto(Lancamento lancamento){
        LancamentoDto lancamentoDto = new LancamentoDto();
        lancamentoDto.setId(Optional.of(lancamento.getId()));
        lancamentoDto.setData(dateFormat.format(lancamento.getData()));
        lancamentoDto.setDescricao(lancamento.getDescricao());
        lancamentoDto.setLocalizacao(lancamento.getLocalizacao());
        lancamentoDto.setTipo(lancamento.getTipo().toString());
        lancamentoDto.setFuncionarioId(lancamento.getFuncionario().getId());
        return lancamentoDto;
    }

    /**
     * Valida se o funcionario existe no sistema
     *
     * @param lancamentoDto
     * @param result
     */
    private void validarFuncionario(LancamentoDto lancamentoDto, BindingResult result){
        if(lancamentoDto.getFuncionarioId() == null){
            result.addError(new ObjectError("funcionario", "Funcionário não informado"));
        }

        log.info("Validando funcionario id: {}", lancamentoDto.getFuncionarioId());
        Optional<Funcionario> funcionario = funcionarioService.buscarPorId(lancamentoDto.getFuncionarioId());
        if(!funcionario.isPresent()){
            result.addError(new ObjectError("funcionario", "Funcionário não encontrado. ID inexistente."));
        }
    }

    /**
     * Converte o Dto para entidade lancamento
     *
     * @param lancamentoDto
     * @return Lancamento
     * @throws ParseException
     */
    private Lancamento convertDtoParaLancamento(LancamentoDto lancamentoDto, BindingResult result) throws ParseException {
        Lancamento lancamento = new Lancamento();

        if(lancamentoDto.getId().isPresent()){
            Optional<Lancamento> lanc = lancamentoService.buscarPorId(lancamentoDto.getId().get());
            if(lanc.isPresent()){
                lancamento = lanc.get();
            }
        }
        else{
            lancamento.setFuncionario(new Funcionario());
            lancamento.getFuncionario().setId(lancamentoDto.getFuncionarioId());
        }
        lancamento.setDescricao(lancamentoDto.getDescricao());
        lancamento.setData(dateFormat.parse(lancamentoDto.getData()));
        lancamento.setLocalizacao(lancamentoDto.getLocalizacao());

        if(EnumUtils.isValidEnum(TipoEnum.class, lancamentoDto.getTipo())){
            lancamento.setTipo(TipoEnum.valueOf(lancamentoDto.getTipo()));
        }
        else{
            result.addError(new ObjectError("tipo", "Tipo inválido."));
        }

        return lancamento;
    }
}
