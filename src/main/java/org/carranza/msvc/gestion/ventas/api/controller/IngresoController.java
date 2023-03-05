package org.carranza.msvc.gestion.ventas.api.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.carranza.msvc.gestion.ventas.api.dto.IngresoDTO;
import org.carranza.msvc.gestion.ventas.api.service.IngresoService;
import org.carranza.msvc.gestion.ventas.api.utils.CodeEnum;
import org.carranza.msvc.gestion.ventas.api.utils.ConstantesUtil;
import org.carranza.msvc.gestion.ventas.api.utils.CustomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(ConstantesUtil.API_INGRESOS)
public class IngresoController {
    private final IngresoService IngresoService;

    public IngresoController(IngresoService IngresoService) {
        this.IngresoService = IngresoService;
    }

    @GetMapping("/findAllPage")
    @ResponseBody
    public ResponseEntity<CustomResponse> findAllPage(Pageable paginador){
        Page<IngresoDTO> lstIngresoDTO= this.IngresoService.findAllPage(paginador);
        if (lstIngresoDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        CustomResponse rpta = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), lstIngresoDTO, "Información encontrada");
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CustomResponse> findAll(){
        List<IngresoDTO> lstIngresoDTO= this.IngresoService.findAll();
        if (lstIngresoDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        CustomResponse rpta = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), lstIngresoDTO, "Información encontrada");
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomResponse> findById(@PathVariable UUID id) {
        var Ingreso = this.IngresoService.findById(id);
        CustomResponse rpta = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), Ingreso, null);
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody IngresoDTO IngresoDTO){
        IngresoDTO Ingreso = this.IngresoService.save(IngresoDTO);
        CustomResponse r = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), Ingreso, "Ingreso registrado correctamente.");
        return new ResponseEntity<>(r, HttpStatus.CREATED);
    }

    @PatchMapping("{id}")
    @ResponseBody
    public ResponseEntity<CustomResponse> update( @PathVariable UUID id,  @RequestBody IngresoDTO IngresoDTO) {
        IngresoDTO result = this.IngresoService.update(id, IngresoDTO);
        CustomResponse rpta = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), result, "Ingreso actualizada correctamente.");
        return new ResponseEntity<>(rpta, HttpStatus.OK);

    }

    @PutMapping("delete/{id}")
    public ResponseEntity<CustomResponse> delete( @PathVariable UUID id,  @RequestBody IngresoDTO IngresoDTO) {
        Boolean result = this.IngresoService.delete(id, IngresoDTO);
        CustomResponse rpta = new CustomResponse(Boolean.TRUE.equals(result)?"1":"0", Boolean.TRUE.equals(result) ? "Eliminado correctamente":"Error al eliminar");
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

}
