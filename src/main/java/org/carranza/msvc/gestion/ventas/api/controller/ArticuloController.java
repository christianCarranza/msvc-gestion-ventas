package org.carranza.msvc.gestion.ventas.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.carranza.msvc.gestion.ventas.api.dto.ArticuloDTO;
import org.carranza.msvc.gestion.ventas.api.service.ArticuloService;
import org.carranza.msvc.gestion.ventas.api.utils.CodeEnum;
import org.carranza.msvc.gestion.ventas.api.utils.ConstantesUtil;
import org.carranza.msvc.gestion.ventas.api.utils.CustomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(ConstantesUtil.API_ARTICULO)
public class ArticuloController {
    private final ArticuloService ArticuloService;

    public ArticuloController(ArticuloService ArticuloService) {
        this.ArticuloService = ArticuloService;
    }

    @GetMapping("/findAllPage")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ALMACENERO') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<CustomResponse> findAllPage(Pageable paginador){
        Page<ArticuloDTO> lstArticuloDTO= this.ArticuloService.findAllPage(paginador);
        if (lstArticuloDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        CustomResponse rpta = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), lstArticuloDTO, "Información encontrada");
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ALMACENERO') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<CustomResponse> findAll(){
        List<ArticuloDTO> lstArticuloDTO= this.ArticuloService.findAll();
        if (lstArticuloDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        CustomResponse rpta = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), lstArticuloDTO, "Información encontrada");
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ALMACENERO') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<CustomResponse> findById(@PathVariable UUID id) {
        var persona = this.ArticuloService.findById(id);
        CustomResponse rpta = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), persona, null);
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

    @GetMapping("/findByCodigo/{codigo}")
    @PreAuthorize("hasRole('ROLE_VENDEDOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<CustomResponse> findByCodigo(@PathVariable String codigo) {
        var persona = this.ArticuloService.findByCodigo(codigo);
        CustomResponse rpta = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), persona, null);
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

    @GetMapping("/byNombre")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ALMACENERO') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<CustomResponse> findByLikeNombre(@RequestParam(name = "nombre", defaultValue = "") String nombre){
        List<ArticuloDTO> lstArticuloDTO= this.ArticuloService.findByLikeNombre(nombre);
        CustomResponse rpta;
        if (lstArticuloDTO.isEmpty()) {
            rpta = new CustomResponse(String.valueOf(HttpStatus.NO_CONTENT.value()), "No se encontraron registros");
        }else{
            rpta = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), lstArticuloDTO, "Información encontrada");
        }
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ALMACENERO') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody ArticuloDTO ArticuloDTO){
        ArticuloDTO Articulo = this.ArticuloService.save(ArticuloDTO);
        CustomResponse r = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), Articulo, "Articulo registrado correctamente.");
        return new ResponseEntity<>(r, HttpStatus.CREATED);
    }

    @PatchMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ALMACENERO') or hasRole('ROLE_ADMIN')")
    @ResponseBody
    public ResponseEntity<CustomResponse> update( @PathVariable UUID id,  @RequestBody ArticuloDTO ArticuloDTO) {
        ArticuloDTO result = this.ArticuloService.update(id, ArticuloDTO);
        CustomResponse rpta = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), result, "Articulo actualizada correctamente.");
        return new ResponseEntity<>(rpta, HttpStatus.OK);

    }

    @PutMapping("delete/{id}")
    @PreAuthorize("hasRole('ROLE_ALMACENERO') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<CustomResponse> delete( @PathVariable UUID id,  @RequestBody ArticuloDTO ArticuloDTO) {
        Boolean result = this.ArticuloService.delete(id, ArticuloDTO);
        CustomResponse rpta = new CustomResponse(Boolean.TRUE.equals(result)?"1":"0", Boolean.TRUE.equals(result) ? "Actulizado correctamente":"Error al eliminar");
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

}
