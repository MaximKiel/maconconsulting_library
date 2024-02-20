package ru.maconconsulting.library.controllers.mvc.projectfields;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maconconsulting.library.dto.projectfields.ProjectSegmentDTO;
import ru.maconconsulting.library.models.projectfields.ProjectSegment;
import ru.maconconsulting.library.services.projectfields.ProjectSegmentsService;
import ru.maconconsulting.library.utils.exceptions.projectfields.ProjectSegmentNotFoundException;
import ru.maconconsulting.library.utils.validators.projectfields.ProjectSegmentValidator;

@Controller
@RequestMapping("/segments")
public class ProjectSegmentsController {

    public static final Logger log = LoggerFactory.getLogger(ProjectTypesController.class);
    private final ProjectSegmentsService projectSegmentsService;
    private final ModelMapper modelMapper;
    private final ProjectSegmentValidator projectSegmentValidator;

    @Autowired
    public ProjectSegmentsController(ProjectSegmentsService projectSegmentsService, ModelMapper modelMapper, ProjectSegmentValidator projectSegmentValidator) {
        this.projectSegmentsService = projectSegmentsService;
        this.modelMapper = modelMapper;
        this.projectSegmentValidator = projectSegmentValidator;
    }

    @GetMapping
    public String getAllSegments(Model model) {
        model.addAttribute("segments", projectSegmentsService.findAllSorted());
        log.info("Go to mvc/projectfields/segments/manage");
        return "mvc/projectfields/segments/manage";
    }

    @GetMapping("/new")
    public String newSegment(@ModelAttribute("segment") ProjectSegmentDTO segmentDTO) {
        log.info("Go to mvc/projectfields/segments/new");
        return "mvc/projectfields/segments/new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("segment") @Valid ProjectSegmentDTO segmentDTO, BindingResult bindingResult) {
        projectSegmentValidator.validate(segmentDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("Go to mvc/projectfields/segments/new");
            return "mvc/projectfields/segments/new";
        }
        projectSegmentsService.save(convertToProjectSegment(segmentDTO));
        log.info("Go to redirect:/segments");
        return "redirect:/segments";
    }

    @GetMapping("/{name}/edit")
    public String edit(Model model, @PathVariable("name") String name) {
        model.addAttribute("segment", convertToProjectSegmentDTO(projectSegmentsService.findByName(name)
                .orElseThrow(() -> new ProjectSegmentNotFoundException("Сегмент рынка  " + name + " не найден"))));
        log.info("Go to mvc/projectfields/segments/edit");
        return "mvc/projectfields/segments/edit";
    }

    @PatchMapping("/{name}")
    public String update(@ModelAttribute("segment") @Valid ProjectSegmentDTO segmentDTO, BindingResult bindingResult,
                         @PathVariable("name") String name) {
        if (bindingResult.hasErrors()) {
            log.info("Go to mvc/projectfields/segments/edit");
            return "mvc/projectfields/segments/edit";
        }

        projectSegmentsService.update(name, convertToProjectSegment(segmentDTO));
        log.info("Go to redirect:/segments");
        return "redirect:/segments";
    }

    @DeleteMapping("/{name}")
    public String delete(@PathVariable("name") String name) {
        projectSegmentsService.delete(name);
        log.info("Go to redirect:/segments");
        return "redirect:/segments";
    }

    private ProjectSegment convertToProjectSegment(ProjectSegmentDTO segmentDTO) {
        return modelMapper.map(segmentDTO, ProjectSegment.class);
    }

    private ProjectSegmentDTO convertToProjectSegmentDTO(ProjectSegment segment) {
        return modelMapper.map(segment, ProjectSegmentDTO.class);
    }
}
