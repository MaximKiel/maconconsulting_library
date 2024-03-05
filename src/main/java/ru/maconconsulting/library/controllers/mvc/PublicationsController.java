package ru.maconconsulting.library.controllers.mvc;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maconconsulting.library.dto.PublicationDTO;
import ru.maconconsulting.library.dto.parameters.FormatDTO;
import ru.maconconsulting.library.dto.parameters.KeyWordDTO;
import ru.maconconsulting.library.dto.parameters.SegmentDTO;
import ru.maconconsulting.library.models.Publication;
import ru.maconconsulting.library.models.parameters.Format;
import ru.maconconsulting.library.models.parameters.KeyWord;
import ru.maconconsulting.library.models.parameters.Segment;
import ru.maconconsulting.library.services.PublicationsService;
import ru.maconconsulting.library.services.parameters.FormatsService;
import ru.maconconsulting.library.services.parameters.KeyWordsService;
import ru.maconconsulting.library.services.parameters.SegmentsService;
import ru.maconconsulting.library.utils.SearchPublication;
import ru.maconconsulting.library.utils.exceptions.PublicationNotFoundException;
import ru.maconconsulting.library.utils.validators.PublicationValidator;

import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/publications")
public class PublicationsController {

    public static final Logger log = LoggerFactory.getLogger(ProjectsController.class);
    private final PublicationsService publicationsService;
    private final PublicationValidator publicationValidator;
    private final SegmentsService segmentsService;
    private final FormatsService formatsService;
    private final KeyWordsService keyWordsService;
    private final ModelMapper modelMapper;

    @Autowired
    public PublicationsController(PublicationsService publicationsService, PublicationValidator publicationValidator, SegmentsService segmentsService, FormatsService formatsService, KeyWordsService keyWordsService, ModelMapper modelMapper) {
        this.publicationsService = publicationsService;
        this.publicationValidator = publicationValidator;
        this.segmentsService = segmentsService;
        this.formatsService = formatsService;
        this.keyWordsService = keyWordsService;
        this.modelMapper = modelMapper;
    }
    @GetMapping
    public String getAllPublications() {
        log.info("Go to mvc/publications/manage");
        return "mvc/publications/manage";
    }

    @GetMapping("/{title}")
    public String show(@PathVariable("title") String title, Model model) {
        log.info("Go to mvc/publications/show");
        model.addAttribute("publication", convertToPublicationDTO(publicationsService.findByTitle(title)
                .orElseThrow(() -> new PublicationNotFoundException("Публикация с названием " + title + " не найдена"))));
        return "mvc/publications/show";
    }

    @GetMapping("/new")
    public String newPublication(@ModelAttribute("publication") PublicationDTO publicationDTO, Model model) {
        model.addAttribute("segments", segmentsService.findAll().stream().sorted(Comparator.comparing(Segment::getName)).collect(Collectors.toList()));
        model.addAttribute("formats", formatsService.findAll().stream().sorted(Comparator.comparing(Format::getName)).collect(Collectors.toList()));
        model.addAttribute("key_words", keyWordsService.findAll().stream().sorted(Comparator.comparing(KeyWord::getName)).collect(Collectors.toList()));
        log.info("Go to mvc/publications/new");
        return "mvc/publications/new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("publication") @Valid PublicationDTO publicationDTO, BindingResult bindingResult) {
        publicationValidator.validate(publicationDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("Go to mvc/publications/new");
            return "mvc/publications/new";
        }
        publicationsService.save(convertToPublication(publicationDTO));
        log.info("Go to redirect:/publications/" + publicationDTO.getTitle());
        return "redirect:/publications/" + publicationDTO.getTitle();
    }

    @GetMapping("/{title}/edit")
    public String edit(Model model, @PathVariable("title") String title) {
        model.addAttribute("publication", convertToPublicationDTO(publicationsService.findByTitle(title)
                .orElseThrow(() -> new PublicationNotFoundException("Публикация с названием " + title + " не найдена"))));
        model.addAttribute("segments", segmentsService.findAll().stream().sorted(Comparator.comparing(Segment::getName)).map(this::convertToProjectSegmentDTO).collect(Collectors.toList()));
        model.addAttribute("formats", formatsService.findAll().stream().sorted(Comparator.comparing(Format::getName)).map(this::convertToProjectFormatDTO).collect(Collectors.toList()));
        model.addAttribute("key_words", keyWordsService.findAll().stream().sorted(Comparator.comparing(KeyWord::getName)).map(this::convertToProjectKeyWordDTO).collect(Collectors.toList()));
        log.info("Go to mvc/publications/edit");
        return "mvc/publications/edit";
    }

    @PatchMapping("/{title}")
    public String update(@ModelAttribute("publication") @Valid PublicationDTO publicationDTO, BindingResult bindingResult,
                         @PathVariable("title") String title) {
        if (bindingResult.hasErrors()) {
            log.info("Go to mvc/publications/edit");
            return "mvc/publications/edit";
        }

        publicationsService.update(title, convertToPublication(publicationDTO));
        log.info("Go to redirect:/publications/" + title);
        return "redirect:/publications/" + title;
    }

    @DeleteMapping("/{title}")
    public String delete(@PathVariable("title") String title) {
        publicationsService.delete(title);
        log.info("Go to redirect:/publications");
        return "redirect:/publications";
    }

    @GetMapping("/search")
    public String search(@ModelAttribute("searchPublication")SearchPublication searchPublication, Model model) {
        model.addAttribute("segments", segmentsService.findAll().stream().sorted(Comparator.comparing(Segment::getName)).collect(Collectors.toList()));
        model.addAttribute("formats", formatsService.findAll().stream().sorted(Comparator.comparing(Format::getName)).collect(Collectors.toList()));
        model.addAttribute("key_words", keyWordsService.findAll().stream().sorted(Comparator.comparing(KeyWord::getName)).collect(Collectors.toList()));
        log.info("Go to mvc/publications/search");
        return "mvc/publications/search";
    }

    @PostMapping("/search-result")
    public String showSearchResult(Model model, @ModelAttribute("searchPublication") SearchPublication searchPublication,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Go to mvc/publications/search");
            return "mvc/publications/search";
        }

        model.addAttribute("result", publicationsService.search(searchPublication));
        log.info("Go to mvc/publications/result");
        return "mvc/publications/result";
    }

    private PublicationDTO convertToPublicationDTO(Publication publication) {
        return modelMapper.map(publication, PublicationDTO.class);
    }

    private Publication convertToPublication(PublicationDTO publicationDTO) {
        return modelMapper.map(publicationDTO, Publication.class);
    }

    private SegmentDTO convertToProjectSegmentDTO(Segment segment) {
        return modelMapper.map(segment, SegmentDTO.class);
    }

    private FormatDTO convertToProjectFormatDTO(Format format) {
        return modelMapper.map(format, FormatDTO.class);
    }

    private KeyWordDTO convertToProjectKeyWordDTO(KeyWord keyWord) {
        return modelMapper.map(keyWord, KeyWordDTO.class);
    }
}
