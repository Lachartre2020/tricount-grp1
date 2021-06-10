package com.natixis.tricount.controller;

import com.natixis.tricount.service.CreateUpdateListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class CreateUpdateListController
{
    @Autowired
    private CreateUpdateListService createUpdateListService ;

    @GetMapping("/lists/{idList}")
    public String createUpdateList(Model model, @PathVariable(required = false) String idList)
    {
        if (idList != null)
        {
//            Optional<School> optionalWizard = schoolRepository.findById(id);
//            if (optionalWizard.isPresent()) {
//                school = optionalWizard.get();
//            }
        }


        return "createUpdateList";
    }

//    @PostMapping("/lists")
//    public String createUpdateList (@RequestParam Long idList)
//    {
//        if (idList != null)
//        {
//
//        }
//        createUpdateListService.save(idList);
//        return "redirect:/lists";
//    }
}
