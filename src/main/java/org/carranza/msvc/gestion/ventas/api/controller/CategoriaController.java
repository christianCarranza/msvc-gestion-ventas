package org.carranza.msvc.gestion.ventas.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.carranza.msvc.gestion.ventas.api.dto.CategoriaDTO;
import org.carranza.msvc.gestion.ventas.api.service.CategoriaService;
import org.carranza.msvc.gestion.ventas.api.utils.CodeEnum;
import org.carranza.msvc.gestion.ventas.api.utils.ConstantesUtil;
import org.carranza.msvc.gestion.ventas.api.utils.CustomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(ConstantesUtil.API_CATEGORIA)
public class CategoriaController {
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping("/findAllPage")
    @ResponseBody
    public ResponseEntity<CustomResponse> findAllPage(Pageable paginador){
        Page<CategoriaDTO> lstCategoriaDTO= this.categoriaService.findAllPage(paginador);
        if (lstCategoriaDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        CustomResponse rpta = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), lstCategoriaDTO, "Información encontrada");
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CustomResponse> findAll(){
        List<CategoriaDTO> lstCategoriaDTO= this.categoriaService.findAll();
        if (lstCategoriaDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        CustomResponse rpta = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), lstCategoriaDTO, "Información encontrada");
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomResponse> findById(@PathVariable UUID id) {
        var persona = this.categoriaService.findById(id);
        CustomResponse rpta = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), persona, null);
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

    @GetMapping("/byNombre")
    @ResponseBody
    public ResponseEntity<CustomResponse> findByLikeNombre(@RequestParam(name = "nombre", defaultValue = "") String nombre){
        List<CategoriaDTO> lstCategoriaDTO= this.categoriaService.findByLikeNombre(nombre);
        CustomResponse rpta;
        if (lstCategoriaDTO.isEmpty()) {
            rpta = new CustomResponse(String.valueOf(HttpStatus.NO_CONTENT.value()), "No se encontraron registros");
        }else{
            rpta = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), lstCategoriaDTO, "Información encontrada");
        }
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomResponse> save(@Valid @RequestBody CategoriaDTO categoriaDTO){
        CategoriaDTO categoria = this.categoriaService.save(categoriaDTO);
        CustomResponse r = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), categoria, "Categoria registrado correctamente.");
        return new ResponseEntity<>(r, HttpStatus.CREATED);
    }

    @PatchMapping("{id}")
    @ResponseBody
    public ResponseEntity<CustomResponse> update( @PathVariable UUID id,  @RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO result = this.categoriaService.update(id, categoriaDTO);
        CustomResponse rpta = new CustomResponse(String.valueOf(CodeEnum.SUCCESS), result, "Categoria actualizada correctamente.");
        return new ResponseEntity<>(rpta, HttpStatus.OK);

    }

    @PutMapping("delete/{id}")
    public ResponseEntity<CustomResponse> delete( @PathVariable UUID id,  @RequestBody CategoriaDTO categoriaDTO) {
        Boolean result = this.categoriaService.delete(id, categoriaDTO);
        CustomResponse rpta = new CustomResponse(Boolean.TRUE.equals(result)?"1":"0", Boolean.TRUE.equals(result) ? "Eliminado correctamente":"Error al eliminar");
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

}
