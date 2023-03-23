package org.carranza.msvc.gestion.ventas.security.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.carranza.msvc.gestion.ventas.api.utils.CodeEnum;
import org.carranza.msvc.gestion.ventas.api.utils.ConstantesUtil;
import org.carranza.msvc.gestion.ventas.api.utils.CustomResponse;
import org.carranza.msvc.gestion.ventas.security.dto.RolDTO;
import org.carranza.msvc.gestion.ventas.security.service.RolService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(ConstantesUtil.API_ROLES)
public class RolController {
    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping("/findAllPage")
    @ResponseBody
    public ResponseEntity<?> findAllPage(Pageable paginador){
        Page<RolDTO> lstRolDTO= this.rolService.findAllPage(paginador);
        if (lstRolDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        CustomResponse rpta = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), lstRolDTO, "Información encontrada");
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CustomResponse> findAll(){
        List<RolDTO> lstRolDTO= this.rolService.findAll();
        if (lstRolDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        CustomResponse rpta = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), lstRolDTO, "Información encontrada");
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomResponse> findById(@PathVariable UUID id) {
        var persona = this.rolService.findById(id);
        CustomResponse rpta = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), persona, null);
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody RolDTO rolDTO){
        RolDTO rol = this.rolService.save(rolDTO);
        CustomResponse r = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), rol, "Rol registrado correctamente.");
        return new ResponseEntity<>(r, HttpStatus.CREATED);
    }

    @PatchMapping("{id}")
    @ResponseBody
    public ResponseEntity<CustomResponse> update( @PathVariable UUID id,  @RequestBody RolDTO rolDTO) {
        RolDTO result = this.rolService.update(id, rolDTO);
        CustomResponse rpta = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), result, "Rol actualizada correctamente.");
        return new ResponseEntity<>(rpta, HttpStatus.OK);

    }

    @PutMapping("delete/{id}")
    public ResponseEntity<CustomResponse> delete( @PathVariable UUID id,  @RequestBody RolDTO rolDTO) {
        Boolean result = this.rolService.delete(id, rolDTO);
        CustomResponse rpta = new CustomResponse(Boolean.TRUE.equals(result)?"1":"0", Boolean.TRUE.equals(result) ? "Actualizado correctamente":"Error al actualizar");
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

}
