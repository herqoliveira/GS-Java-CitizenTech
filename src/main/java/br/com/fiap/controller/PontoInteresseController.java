package br.com.fiap.controller;

import br.com.fiap.model.entity.PontoInteresse;
import br.com.fiap.model.model.dto.PontoInteresseDTO;
import br.com.fiap.model.repository.FonteMapaRepository;
import br.com.fiap.model.repository.PontoInteresseRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/pontos")
public class PontoInteresseController {

    @Autowired
    private PontoInteresseRepository pontoInteresseRepository;

    @Autowired
    private FonteMapaRepository fonteMapaRepository;

    @GetMapping
    public ModelAndView consultar() {
        List<PontoInteresse> pontos = pontoInteresseRepository.findAll();

        ModelAndView mv = new ModelAndView("pontos");
        mv.addObject("pontos", pontos);

        return mv;
    }

    @GetMapping("/novo")
    public ModelAndView novo() {
        ModelAndView mv = new ModelAndView("formulario-ponto");
        mv.addObject("fontes", fonteMapaRepository.findAll());

        return mv;
    }

    @PostMapping
    public ModelAndView cadastrar(@Valid PontoInteresseDTO pontoInteresseDTO,
                                  BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("redirect:/pontos/novo");
        }

        if (!fonteMapaRepository.existsById(pontoInteresseDTO.idFonte())) {
            return new ModelAndView("redirect:/pontos/novo");
        }

        PontoInteresse ponto = new PontoInteresse(pontoInteresseDTO);
        pontoInteresseRepository.save(ponto);

        return new ModelAndView("redirect:/pontos");
    }

    @PostMapping("/confirmar/{idPonto}")
    public ModelAndView confirmar(@PathVariable Long idPonto) {
        PontoInteresse ponto =
                pontoInteresseRepository.findByIdPonto(idPonto);

        if (ponto == null) {
            return new ModelAndView("redirect:/pontos");
        }

        ponto.setStatusVerificacao("CONFIRMADO");
        pontoInteresseRepository.save(ponto);

        return new ModelAndView("redirect:/pontos");
    }

    @GetMapping("/editar/{idPonto}")
    public ModelAndView consultarPorCodigo(@PathVariable Long idPonto) {
        PontoInteresse ponto = pontoInteresseRepository.findByIdPonto(idPonto);
        if (ponto == null) {
            return new ModelAndView("redirect:/pontos");
        }
        ModelAndView mv = new ModelAndView("formulario-ponto");
        mv.addObject("ponto", ponto);
        mv.addObject("fontes", fonteMapaRepository.findAll());
        mv.addObject("acao", "/pontos/atualizar/" + idPonto);
        return mv;
    }

    @PostMapping("/atualizar/{idPonto}")
    public ModelAndView atualizar(@PathVariable Long idPonto, @Valid PontoInteresseDTO pontoInteresseDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("redirect:/pontos/editar/" + idPonto);
        }
        PontoInteresse ponto = pontoInteresseRepository.findByIdPonto(idPonto);
        if (ponto == null) {
            return new ModelAndView("redirect:/pontos");
        }
        if (!fonteMapaRepository.existsById(pontoInteresseDTO.idFonte())) {
            return new ModelAndView("redirect:/pontos/editar/" + idPonto);
        }
        ponto.setIdFonte(pontoInteresseDTO.idFonte());
        ponto.setNome(pontoInteresseDTO.nome());
        ponto.setTipo(pontoInteresseDTO.tipo());
        ponto.setLatitude(pontoInteresseDTO.latitude());
        ponto.setLongitude(pontoInteresseDTO.longitude());
        ponto.setObservacao(pontoInteresseDTO.observacao());
        ponto.setStatusVerificacao("PENDENTE");
        pontoInteresseRepository.save(ponto);
        return new ModelAndView("redirect:/pontos");
    }

    @GetMapping("/excluir/{idPonto}")
    public ModelAndView excluir(@PathVariable Long idPonto) {
        if (pontoInteresseRepository.existsById(idPonto)) {
            pontoInteresseRepository.deleteById(idPonto);
        }
        return new ModelAndView("redirect:/pontos");
    }

    @PostMapping("/pendente/{idPonto}")
    public ModelAndView marcarPendente(@PathVariable Long idPonto) {
        PontoInteresse ponto = pontoInteresseRepository.findByIdPonto(idPonto);
        if (ponto == null) {
            return new ModelAndView("redirect:/pontos");
        }
        ponto.setStatusVerificacao("PENDENTE");
        pontoInteresseRepository.save(ponto);
        return new ModelAndView("redirect:/pontos");
    }
}