package br.com.fiap.controller;

import br.com.fiap.model.entity.FonteMapa;
import br.com.fiap.model.model.dto.FonteMapaDTO;
import br.com.fiap.model.repository.FonteMapaRepository;
import br.com.fiap.model.repository.MapaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import br.com.fiap.model.entity.PontoInteresse;
import br.com.fiap.model.repository.PontoInteresseRepository;

import java.util.List;

@RestController
@RequestMapping("/fontes")
public class FonteMapaController {

    @Autowired
    private FonteMapaRepository fonteMapaRepository;

    @Autowired
    private MapaRepository mapaRepository;

    @Autowired
    private PontoInteresseRepository pontoInteresseRepository;

    @GetMapping
    public ModelAndView consultar() {
        List<FonteMapa> fontes = fonteMapaRepository.findAll();
        ModelAndView mv = new ModelAndView("fontes");
        mv.addObject("fontes", fontes);
        return mv;
    }

    @GetMapping("/novo")
    public ModelAndView novo() {
        ModelAndView mv = new ModelAndView("formulario-fonte");
        mv.addObject("mapas", mapaRepository.findAll());
        return mv;
    }

    @PostMapping
    public ModelAndView cadastrar(@Valid FonteMapaDTO fonteMapaDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("redirect:/fontes/novo");
        }
        if (!mapaRepository.existsById(fonteMapaDTO.idMapa())) {
            return new ModelAndView("redirect:/fontes/novo");
        }
        FonteMapa fonte = new FonteMapa(fonteMapaDTO);
        fonteMapaRepository.save(fonte);
        return new ModelAndView("redirect:/fontes");
    }

    @GetMapping("/editar/{idFonte}")
    public ModelAndView consultarPorCodigo(@PathVariable Long idFonte) {
        FonteMapa fonte = fonteMapaRepository.findByIdFonte(idFonte);
        if (fonte == null) {
            return new ModelAndView("redirect:/fontes");
        }
        ModelAndView mv = new ModelAndView("formulario-fonte");
        mv.addObject("fonte", fonte);
        mv.addObject("mapas", mapaRepository.findAll());
        mv.addObject("acao", "/fontes/atualizar/" + idFonte);
        return mv;
    }

    @PostMapping("/atualizar/{idFonte}")
    public ModelAndView atualizar(@PathVariable Long idFonte, @Valid FonteMapaDTO fonteMapaDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("redirect:/fontes/editar/" + idFonte);
        }
        FonteMapa fonte = fonteMapaRepository.findByIdFonte(idFonte);
        if (fonte == null) {
            return new ModelAndView("redirect:/fontes");
        }
        if (!mapaRepository.existsById(fonteMapaDTO.idMapa())) {
            return new ModelAndView("redirect:/fontes/editar/" + idFonte);
        }
        fonte.setIdMapa(fonteMapaDTO.idMapa());
        fonte.setTitulo(fonteMapaDTO.titulo());
        fonte.setUrl(fonteMapaDTO.url());
        fonteMapaRepository.save(fonte);
        return new ModelAndView("redirect:/fontes");
    }

    @GetMapping("/excluir/{idFonte}")
    public ModelAndView excluir(@PathVariable Long idFonte) {
        try {
            if (!fonteMapaRepository.existsById(idFonte)) {
                return new ModelAndView("redirect:/fontes");
            }
            List<PontoInteresse> pontos = pontoInteresseRepository.findByIdFonte(idFonte);
            if (pontos.size() > 0) {
                List<FonteMapa> fontes = fonteMapaRepository.findAll();
                ModelAndView mv = new ModelAndView("fontes");
                mv.addObject("fontes", fontes);
                mv.addObject("mensagem", "Esta fonte possui pontos cadastrados e não pode ser excluída.");
                return mv;
            }
            fonteMapaRepository.deleteById(idFonte);
            return new ModelAndView("redirect:/fontes");
        } catch (Exception e) {
            ModelAndView mv = new ModelAndView("erro");
            mv.addObject("mensagem", "Não foi possível excluir a fonte. Tente novamente mais tarde.");
            return mv;
        }
    }

    @GetMapping("/detalhes/{idFonte}")
    public ModelAndView detalhar(@PathVariable Long idFonte) {
        FonteMapa fonte = fonteMapaRepository.findByIdFonte(idFonte);
        if (fonte == null) {
            return new ModelAndView("redirect:/fontes");
        }
        List<PontoInteresse> pontos = pontoInteresseRepository.findByIdFonte(idFonte);
        ModelAndView mv = new ModelAndView("detalhe-fonte");
        mv.addObject("fonte", fonte);
        mv.addObject("pontos", pontos);
        return mv;
    }
}