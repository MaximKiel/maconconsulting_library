package ru.maconconsulting.library.controllers.content;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maconconsulting.library.dto.content.PublicationDTO;
import ru.maconconsulting.library.dto.parameters.*;
import ru.maconconsulting.library.models.content.Publication;
import ru.maconconsulting.library.models.parameters.*;
import ru.maconconsulting.library.services.content.PublicationsService;
import ru.maconconsulting.library.services.parameters.*;
import ru.maconconsulting.library.utils.search.SearchPublication;
import ru.maconconsulting.library.utils.exceptions.content.PublicationNotFoundException;
import ru.maconconsulting.library.utils.validators.content.PublicationValidator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/publications")
public class PublicationsController {

    public static final Logger log = LoggerFactory.getLogger(PublicationsController.class);
    private final PublicationsService publicationsService;
    private final PublicationValidator publicationValidator;
    private final SegmentsService segmentsService;
    private final FormatsService formatsService;
    private final ModelMapper modelMapper;

    @Autowired
    public PublicationsController(PublicationsService publicationsService, PublicationValidator publicationValidator, SegmentsService segmentsService, FormatsService formatsService, ModelMapper modelMapper) {
        this.publicationsService = publicationsService;
        this.publicationValidator = publicationValidator;
        this.segmentsService = segmentsService;
        this.formatsService = formatsService;
        this.modelMapper = modelMapper;
    }
    @GetMapping
    public String getAllPublications() {
        log.info("Go to content/publications/manage");
        return "content/publications/manage";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("publication", convertToPublicationDTO(publicationsService.findById(id)
                .orElseThrow(() -> new PublicationNotFoundException("Публикация не найдена"))));
        log.info("Go to content/publications/show");
        return "content/publications/show";
    }

    @GetMapping("/new")
    public String newPublication(@ModelAttribute("publication") PublicationDTO publicationDTO, Model model) {
        addParametersToModelAttribute(model);
        log.info("Go to content/publications/new");
        return "content/publications/new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("publication") @Valid PublicationDTO publicationDTO, BindingResult bindingResult,
                         Model model) {
        publicationValidator.validate(publicationDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            addParametersToModelAttribute(model);
            checkNotNullParameters(publicationDTO);
            log.info("Go to content/publications/new");
            return "content/publications/new";
        }
        publicationsService.save(convertToPublication(publicationDTO));
        log.info("Go to redirect:/publications/" + publicationsService.findByTitle(publicationDTO.getTitle()).get().getId());
        return "redirect:/publications/" + publicationsService.findByTitle(publicationDTO.getTitle()).get().getId();
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("publication", convertToPublicationDTO(publicationsService.findById(id)
                .orElseThrow(() -> new PublicationNotFoundException("Публикация не найдена"))));
//        Use DTO parameters because they store into PublicationDTO for update
        addParametersDTOToModelAttribute(model);
        log.info("Go to content/publications/edit");
        return "content/publications/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("publication") @Valid PublicationDTO publicationDTO, BindingResult bindingResult,
                         @PathVariable("id") Integer id, Model model) {
        publicationValidator.checkTitleForUpdate(publicationDTO, bindingResult);
        if (bindingResult.hasErrors()) {
//        Use DTO parameters because they store into PublicationDTO for update
            addParametersDTOToModelAttribute(model);
            checkNotNullParameters(publicationDTO);
            log.info("Go to content/publications/edit");
            return "content/publications/edit";
        }

        publicationsService.update(id, convertToPublication(publicationDTO));
        log.info("Go to redirect:/publications/" + id);
        return "redirect:/publications/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        publicationsService.delete(id);
        log.info("Go to redirect:/publications");
        return "redirect:/publications";
    }

    @GetMapping("/search")
    public String search(@ModelAttribute("searchPublication")SearchPublication searchPublication, Model model) {
        addParametersToModelAttribute(model);
        log.info("Go to content/publications/search");
        return "content/publications/search";
    }

    @PostMapping("/search-result")
    public String showSearchResult(Model model, @ModelAttribute("searchPublication") SearchPublication searchPublication,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Go to content/publications/search");
            return "content/publications/search";
        }

        model.addAttribute("result", publicationsService.search(searchPublication));
        log.info("Go to content/publications/result");
        return "content/publications/result";
    }

    @ExceptionHandler
    private String handleException(PublicationNotFoundException e) {
        return "content/publications/not_found";
    }

//    For new, create and search methods
    private void addParametersToModelAttribute(Model model) {
        model.addAttribute("segments", segmentsService.findAll().stream().sorted(Comparator.comparing(Segment::getName)).collect(Collectors.toList()));
        model.addAttribute("formats", formatsService.findAll().stream().sorted(Comparator.comparing(Format::getName)).collect(Collectors.toList()));
    }

//    For edit and update methods
    private void addParametersDTOToModelAttribute(Model model) {
        model.addAttribute("segments", segmentsService.findAll().stream().sorted(Comparator.comparing(Segment::getName)).map(this::convertToSegmentDTO).collect(Collectors.toList()));
        model.addAttribute("formats", formatsService.findAll().stream().sorted(Comparator.comparing(Format::getName)).map(this::convertToFormatDTO).collect(Collectors.toList()));
    }

    private void checkNotNullParameters(PublicationDTO publicationDTO) {
        if (publicationDTO.getSegments() == null) {
            publicationDTO.setSegments(new ArrayList<>());
        }
        if (publicationDTO.getFormats() == null) {
            publicationDTO.setFormats(new ArrayList<>());
        }
    }

    private PublicationDTO convertToPublicationDTO(Publication publication) {
        return modelMapper.map(publication, PublicationDTO.class);
    }

    private Publication convertToPublication(PublicationDTO publicationDTO) {
        return modelMapper.map(publicationDTO, Publication.class);
    }

    private SegmentDTO convertToSegmentDTO(Segment segment) {
        return modelMapper.map(segment, SegmentDTO.class);
    }

    private FormatDTO convertToFormatDTO(Format format) {
        return modelMapper.map(format, FormatDTO.class);
    }
}
