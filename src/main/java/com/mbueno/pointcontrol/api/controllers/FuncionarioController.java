package com.mbueno.pointcontrol.api.controllers;

import com.mbueno.pointcontrol.api.dtos.FuncionarioDto;
import com.mbueno.pointcontrol.api.entities.Funcionario;
import com.mbueno.pointcontrol.api.response.Response;
import com.mbueno.pointcontrol.api.services.FuncionarioService;
import com.mbueno.pointcontrol.api.utils.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = "*")
public class FuncionarioController {

    private static final Logger log = LoggerFactory.getLogger(FuncionarioController.class);

    @Autowired
    private FuncionarioService funcionarioService;

    /**
     * Atualiza dados do funcionario por ID.
     *
     * @param id
     * @param funcionarioDto
     * @param result
     * @return ResponseEntity<Response < FuncionarioDto>>
     * @throws NoSuchAlgorithmException
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<FuncionarioDto>> atualizar(@PathVariable("id") Long id,
                                                              @Valid @RequestBody FuncionarioDto funcionarioDto,
                                                              BindingResult result) throws NoSuchAlgorithmException {
        log.info("Atualizando funcionário: {} " + funcionarioDto.toString());
        Response<FuncionarioDto> response = new Response<>();

        Optional<Funcionario> funcionario = funcionarioService.buscarPorId(id);

        if (!funcionario.isPresent()) {
            result.addError(new ObjectError("funcionario", "Funcionário não encontrado."));
            return getResponseResponseEntity(result, response);
        }

        atualizarDadosFuncionario(funcionario.get(), funcionarioDto, result);

        if (result.hasErrors()) {
            log.info("Erro validando funcionario: {}", result.getAllErrors());
            return getResponseResponseEntity(result, response);
        }
        funcionarioService.persistir(funcionario.get());
        response.setData(convertFuncionarioDto(funcionario.get()));
        return ResponseEntity.ok(response);
    }

    /**
     * Método que irá adicionar os erros no response e retornar a requisição
     *
     * @param result
     * @param response
     * @return
     */
    private ResponseEntity<Response<FuncionarioDto>> getResponseResponseEntity(BindingResult result, Response<FuncionarioDto> response) {
        result.getAllErrors().forEach(erro -> response.getErrors().add(erro.getDefaultMessage()));
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Atualiza os dados do funcionario com base nos dados do DTO.
     *
     * @param funcionario
     * @param funcionarioDto
     * @param result
     */
    private void atualizarDadosFuncionario(Funcionario funcionario, FuncionarioDto funcionarioDto, BindingResult result)
            throws NoSuchAlgorithmException {
        funcionario.setNome(funcionarioDto.getNome());

        if (!funcionario.getEmail().equals(funcionarioDto.getEmail())) {
            funcionarioService.buscarPorEmail(funcionarioDto.getEmail())
                    .ifPresent(func -> result.addError(
                            new ObjectError("funcionario", "E-mail já existente.")));
            funcionario.setEmail(funcionarioDto.getEmail());
        }

        funcionario.setQtdHorasAlmoco(null);
        funcionarioDto.getQtdHorasAlmoco()
                .ifPresent(qtdHorasAlmoco -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));

        funcionario.setQtdHorasTrabalhoDia(null);
        funcionarioDto.getQtdHorasTrabalhoDia()
                .ifPresent(qtdHorasTrabDia -> funcionario.setQtdHorasTrabalhoDia(Float.valueOf(qtdHorasTrabDia)));

        funcionario.setValorHora(null);
        funcionarioDto.getValorHora()
                .ifPresent(valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));

        funcionarioDto.getSenha()
                .ifPresent(senha -> funcionario.setSenha(PasswordUtils.gerarBCrypt(senha)));
//        if(funcionarioDto.getSenha().isPresent()){
//            funcionario.setSenha(PasswordUtils.gerarBCrypt(funcionarioDto.getSenha().get()));
//        }
    }

    /**
     * Retorna um funcionarioDto com os dados de um funcionario
     *
     * @param funcionario
     * @return FuncionarioDto
     */
    private FuncionarioDto convertFuncionarioDto(Funcionario funcionario) {
        FuncionarioDto funcionarioDto = new FuncionarioDto();
        funcionarioDto.setId(funcionario.getId());
        funcionarioDto.setNome(funcionario.getNome());
        funcionarioDto.setEmail(funcionario.getEmail());
        funcionario.getValorHoraOpt()
                .ifPresent(valorHora -> funcionarioDto.setValorHora(Optional.of(valorHora.toString())));
        funcionario.getQtdHorasAlmocoOpt()
                .ifPresent(qtdHorasAlmoco -> funcionarioDto.setQtdHorasAlmoco(Optional.of(qtdHorasAlmoco.toString())));
        funcionario.getQtdHorasTrabalhoDiaOpt()
                .ifPresent(qtdHorasTrabDia -> funcionarioDto.setQtdHorasTrabalhoDia(Optional.of(qtdHorasTrabDia.toString())));
        return funcionarioDto;
    }
}
