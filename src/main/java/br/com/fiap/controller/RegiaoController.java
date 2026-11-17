package br.com.fiap.controller;

import br.com.fiap.model.entity.Regiao;
import br.com.fiap.model.model.dto.RegiaoDTO;
import br.com.fiap.model.repository.RegiaoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import br.com.fiap.model.entity.Mapa;
import br.com.fiap.model.repository.MapaRepository;

import java.util.List;

@RestController
@RequestMapping("/regioes")
public class RegiaoController {

    @Autowired
    private RegiaoRepository regiaoRepository;

    @Autowired
    private MapaRepository mapaRepository;

    @GetMapping
    public ModelAndView consultar() {
        List<Regiao> regioes = regiaoRepository.findAll();
        ModelAndView mv = new ModelAndView("regioes");
        mv.addObject("regioes", regioes);
        return mv;
    }

    @GetMapping("/novo")
    public ModelAndView novo() {
        return new ModelAndView("formulario-regiao");
    }

    @PostMapping
    public ModelAndView cadastrar(@Valid RegiaoDTO regiaoDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("redirect:/regioes/novo");
        }
        Regiao regiao = new Regiao(regiaoDTO);
        regiaoRepository.save(regiao);
        return new ModelAndView("redirect:/regioes");
    }

    @GetMapping("/editar/{idRegiao}")
    public ModelAndView consultarPorCodigo(@PathVariable Long idRegiao) {
        Regiao regiao = regiaoRepository.findByIdRegiao(idRegiao);
        if (regiao == null) {
            return new ModelAndView("redirect:/regioes");
        }
        ModelAndView mv = new ModelAndView("formulario-regiao");
        mv.addObject("regiao", regiao);
        mv.addObject("acao", "/regioes/atualizar/" + idRegiao);
        return mv;
    }

    @PostMapping("/atualizar/{idRegiao}")
    public ModelAndView atualizar(@PathVariable Long idRegiao, @Valid RegiaoDTO regiaoDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("redirect:/regioes/editar/" + idRegiao);
        }
        Regiao regiao = regiaoRepository.findByIdRegiao(idRegiao);
        if (regiao == null) {
            return new ModelAndView("redirect:/regioes");
        }
        regiao.setNome(regiaoDTO.nome());
        regiao.setMunicipio(regiaoDTO.municipio());
        regiao.setUf(regiaoDTO.uf());
        regiao.setLatitude(regiaoDTO.latitude());
        regiao.setLongitude(regiaoDTO.longitude());
        regiaoRepository.save(regiao);
        return new ModelAndView("redirect:/regioes");
    }

    @GetMapping("/excluir/{idRegiao}")
    public ModelAndView excluir(@PathVariable Long idRegiao) {
        try {
            if (!regiaoRepository.existsById(idRegiao)) {
                return new ModelAndView("redirect:/regioes");
            }
            List<Mapa> mapas = mapaRepository.findByIdRegiao(idRegiao);
            if (mapas.size() > 0) {
                List<Regiao> regioes = regiaoRepository.findAll();
                ModelAndView mv = new ModelAndView("regioes");
                mv.addObject("regioes", regioes);
                mv.addObject("mensagem", "Esta região possui estudos cadastrados e não pode ser excluída.");
                return mv;
            }
            regiaoRepository.deleteById(idRegiao);
            return new ModelAndView("redirect:/regioes");
        } catch (Exception e) {
            ModelAndView mv = new ModelAndView("erro");
            mv.addObject("mensagem", "Não foi possível excluir a região. Tente novamente mais tarde.");
            return mv;
        }
    }
}