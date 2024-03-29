package ru.maconconsulting.library.controllers.mvc.parameters;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maconconsulting.library.dto.parameters.SegmentDTO;
import ru.maconconsulting.library.models.content.Project;
import ru.maconconsulting.library.models.content.Publication;
import ru.maconconsulting.library.models.parameters.Segment;
import ru.maconconsulting.library.services.parameters.SegmentsService;
import ru.maconconsulting.library.utils.exceptions.parameters.SegmentNotFoundException;
import ru.maconconsulting.library.utils.validators.parameters.SegmentValidator;

import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/segments")
public class SegmentsController {

    public static final Logger log = LoggerFactory.getLogger(TypesController.class);
    private final SegmentsService segmentsService;
    private final ModelMapper modelMapper;
    private final SegmentValidator segmentValidator;

    @Autowired
    public SegmentsController(SegmentsService segmentsService, ModelMapper modelMapper, SegmentValidator segmentValidator) {
        this.segmentsService = segmentsService;
        this.modelMapper = modelMapper;
        this.segmentValidator = segmentValidator;
    }

    @GetMapping
    public String getAllSegments(Model model) {
        model.addAttribute("segments", segmentsService.findAll().stream().sorted(Comparator.comparing(Segment::getName)).collect(Collectors.toList()));
        log.info("Go to mvc/parameters/segments/manage");
        return "mvc/parameters/segments/manage";
    }

    @GetMapping("/new")
    public String newSegment(@ModelAttribute("segment") SegmentDTO segmentDTO) {
        log.info("Go to mvc/parameters/segments/new");
        return "mvc/parameters/segments/new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("segment") @Valid SegmentDTO segmentDTO, BindingResult bindingResult) {
        segmentValidator.validate(segmentDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("Go to mvc/parameters/segments/new");
            return "mvc/parameters/segments/new";
        }
        segmentsService.save(convertToProjectSegment(segmentDTO));
        log.info("Go to redirect:/segments");
        return "redirect:/segments";
    }

    @GetMapping("/{name}/edit")
    public String edit(Model model, @PathVariable("name") String name) {
        model.addAttribute("segment", convertToProjectSegmentDTO(segmentsService.findByName(name)
                .orElseThrow(() -> new SegmentNotFoundException("Сегмент рынка  " + name + " не найден"))));
        log.info("Go to mvc/parameters/segments/edit");
        return "mvc/parameters/segments/edit";
    }

    @PatchMapping("/{name}")
    public String update(@ModelAttribute("segment") @Valid SegmentDTO segmentDTO, BindingResult bindingResult,
                         @PathVariable("name") String name) {
        segmentValidator.validate(segmentDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("Go to mvc/parameters/segments/edit");
            return "mvc/parameters/segments/edit";
        }

        segmentsService.update(name, convertToProjectSegment(segmentDTO));
        log.info("Go to redirect:/segments");
        return "redirect:/segments";
    }

    @DeleteMapping("/{name}")
    public String delete(@PathVariable("name") String name, Model model) {
        if (segmentsService.findByName(name).isPresent() && !segmentsService.findByName(name).get().getProjects().isEmpty()) {
            model.addAttribute("projects", segmentsService.findByName(name).get().getProjects().stream().sorted(Comparator.comparing(Project::getNumber)).collect(Collectors.toList()));
            model.addAttribute("publications", segmentsService.findByName(name).get().getPublications().stream().sorted(Comparator.comparing(Publication::getTitle)).collect(Collectors.toList()));
            log.info("mvc/parameters/delete_error");
            return "mvc/parameters/delete_error";
        }

        segmentsService.delete(name);
        log.info("Go to redirect:/segments");
        return "redirect:/segments";
    }

    private Segment convertToProjectSegment(SegmentDTO segmentDTO) {
        return modelMapper.map(segmentDTO, Segment.class);
    }

    private SegmentDTO convertToProjectSegmentDTO(Segment segment) {
        return modelMapper.map(segment, SegmentDTO.class);
    }
}
