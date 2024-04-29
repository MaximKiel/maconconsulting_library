package ru.maconconsulting.library.controllers.parameters;

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

    public static final Logger log = LoggerFactory.getLogger(SegmentsController.class);
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
    public String getAll(Model model) {
        model.addAttribute("segments", segmentsService.findAll().stream().sorted(Comparator.comparing(Segment::getName)).collect(Collectors.toList()));
        log.info("Show manage segments view - call SegmentsController method getAll()");
        return "parameters/segments/manage";
    }

    @GetMapping("/new")
    public String newSegment(@ModelAttribute("segment") SegmentDTO segmentDTO) {
        log.info("Show view for create new segment - call SegmentsController method newSegment()");
        return "parameters/segments/new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("segment") @Valid SegmentDTO segmentDTO, BindingResult bindingResult) {
        segmentValidator.validate(segmentDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("Catch error for create new segment - recall SegmentsController method newSegment()");
            return "parameters/segments/new";
        }
        segmentsService.save(convertToSegment(segmentDTO));
        Segment segment = segmentsService.findByName(segmentDTO.getName())
                .orElseThrow(() -> new SegmentNotFoundException("Сегмент " + segmentDTO.getName() + " не найден"));
        log.info("Create new segment with ID=" + segment.getId() + " - redirect to SegmentsController method show()");
        return "redirect:/segments";
    }

    @GetMapping("/{name}/edit")
    public String edit(Model model, @PathVariable("name") String name) {
        model.addAttribute("segment", convertToSegmentDTO(segmentsService.findByName(name)
                .orElseThrow(() -> new SegmentNotFoundException("Сегмент  " + name + " не найден"))));
        log.info("Show view for edit segment with name=" + name + " - call SegmentsController method edit()");
        return "parameters/segments/edit";
    }

    @PatchMapping("/{name}")
    public String update(@ModelAttribute("segment") @Valid SegmentDTO segmentDTO, BindingResult bindingResult,
                         @PathVariable("name") String name) {
        segmentValidator.validate(segmentDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("Catch error for update segment with name=" + name + " - recall SegmentsController method edit()");
            return "parameters/segments/edit";
        }

        segmentsService.update(name, convertToSegment(segmentDTO));
        log.info("Update segment with name=" + name + " - redirect to SegmentsController method show()");
        return "redirect:/segments";
    }

    @DeleteMapping("/{name}")
    public String delete(@PathVariable("name") String name, Model model) {
        if (segmentsService.findByName(name).isPresent() && (!segmentsService.findByName(name).get().getProjects().isEmpty() || !segmentsService.findByName(name).get().getPublications().isEmpty() )) {
            model.addAttribute("projects", segmentsService.findByName(name).get().getProjects().stream().sorted(Comparator.comparing(Project::getTitle)).collect(Collectors.toList()));
            model.addAttribute("publications", segmentsService.findByName(name).get().getPublications().stream().sorted(Comparator.comparing(Publication::getTitle)).collect(Collectors.toList()));
            log.info("Catch error for delete segment with name=" + name + " - show delete parameter page");
            return "parameters/delete_error";
        }

        segmentsService.delete(name);
        log.info("Delete segment with name=" + name + " - redirect to SegmentsController method getAll()");
        return "redirect:/segments";
    }

    @ExceptionHandler
    private String handleException(SegmentNotFoundException e) {
        log.info("Catch SegmentNotFoundException: " + e.getMessage());
        return "parameters/segments/not_found";
    }

    private Segment convertToSegment(SegmentDTO segmentDTO) {
        return modelMapper.map(segmentDTO, Segment.class);
    }

    private SegmentDTO convertToSegmentDTO(Segment segment) {
        return modelMapper.map(segment, SegmentDTO.class);
    }
}
