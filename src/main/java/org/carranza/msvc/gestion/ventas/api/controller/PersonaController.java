package org.carranza.msvc.gestion.ventas.api.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.carranza.msvc.gestion.ventas.api.dto.PersonaDTO;
import org.carranza.msvc.gestion.ventas.api.service.PersonaService;
import org.carranza.msvc.gestion.ventas.api.utils.CodeEnum;
import org.carranza.msvc.gestion.ventas.api.utils.ConstantesUtil;
import org.carranza.msvc.gestion.ventas.api.utils.CustomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(ConstantesUtil.API_PERSONA)
public class PersonaController {
    private final PersonaService PersonaService;

    public PersonaController(PersonaService PersonaService) {
        this.PersonaService = PersonaService;
    }

    @GetMapping("/findAllPageCliente")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_VENDEDOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<CustomResponse> findAllPageCliente(Pageable paginador){
        Page<PersonaDTO> lstPersonaDTO= this.PersonaService.findAllPageCliente(paginador);
        if (lstPersonaDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        CustomResponse rpta = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), lstPersonaDTO, "Información encontrada");
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

    @GetMapping("/findAllPageProveedor")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ALMACENERO') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<CustomResponse> findAllPageProveedor(Pageable paginador){
        Page<PersonaDTO> lstPersonaDTO= this.PersonaService.findAllPageProveedor(paginador);
        if (lstPersonaDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        CustomResponse rpta = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), lstPersonaDTO, "Información encontrada");
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CustomResponse> findAll(){
        List<PersonaDTO> lstPersonaDTO= this.PersonaService.findAll();
        if (lstPersonaDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        CustomResponse rpta = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), lstPersonaDTO, "Información encontrada");
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomResponse> findById(@PathVariable UUID id) {
        var persona = this.PersonaService.findById(id);
        CustomResponse rpta = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), persona, null);
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody PersonaDTO PersonaDTO){
        PersonaDTO Persona = this.PersonaService.save(PersonaDTO);
        CustomResponse r = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), Persona, "Persona registrado correctamente.");
        return new ResponseEntity<>(r, HttpStatus.CREATED);
    }

    @PatchMapping("{id}")
    @ResponseBody
    public ResponseEntity<CustomResponse> update( @PathVariable UUID id,  @RequestBody PersonaDTO PersonaDTO) {
        PersonaDTO result = this.PersonaService.update(id, PersonaDTO);
        CustomResponse rpta = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), result, "Persona actualizada correctamente.");
        return new ResponseEntity<>(rpta, HttpStatus.OK);

    }

    @PutMapping("delete/{id}")
    public ResponseEntity<CustomResponse> delete( @PathVariable UUID id,  @RequestBody PersonaDTO PersonaDTO) {
        Boolean result = this.PersonaService.delete(id, PersonaDTO);
        CustomResponse rpta = new CustomResponse(Boolean.TRUE.equals(result)?"1":"0", Boolean.TRUE.equals(result) ? "Eliminado correctamente":"Error al eliminar");
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

}
