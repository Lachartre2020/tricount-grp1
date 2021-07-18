package com.natixis.tricount.controller;

import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.natixis.tricount.dto.BeneficiaireWithSelection;
import com.natixis.tricount.dto.BeneficiaireWithSelectionListWrapper;
import com.natixis.tricount.entity.Expense;
import com.natixis.tricount.entity.Participant;
import com.natixis.tricount.service.CreateUpdateListService;
import com.natixis.tricount.service.ExpensesOfListSrevice;
import com.natixis.tricount.service.ParticipantService;
import com.natixis.tricount.service.VisuUpdateExpenseService;


@Controller
public class VisuUpdateExpenseController {

	private ArrayList<BeneficiaireWithSelection> allBeneficiaireWithSelection = new ArrayList<BeneficiaireWithSelection>();
	
    @Autowired
    private VisuUpdateExpenseService visuUpdateExpenseService ;

    @Autowired
    private ParticipantService participantService ;

    @Autowired
    private ExpensesOfListSrevice expenseOfListService ;
    
    @Autowired
    private CreateUpdateListService createUpdateListService ;
    
     public VisuUpdateExpenseController(){
    }

    @GetMapping("/expenses/{idExpense}")
    public String visuExpense(Model model, @PathVariable Long idExpense){
    	//Récupération de la dépenses	
    	Optional<Expense> OptionalExpense = expenseOfListService.findById(idExpense);
    	if (OptionalExpense.isPresent()) {
			//Récupération de la liste des bénéficaires (participants) si elle existe
			Expense expense = new Expense();
			expense = OptionalExpense.get();
		
   			model.addAttribute("expense", expense);			
			Set<Participant> BeneficiaireList = OptionalExpense.get().getParticipants();
   		
    		//Récupération de l'id de la liste des dépenses
			Long idEexpenseList = OptionalExpense.get().getExpenselist().getId();
	    	
			//Récupération de la liste des participants relatifs à la liste de dépenses 
			List<Participant> participantList = createUpdateListService.findById(idEexpenseList).get().getParticipants();
			if ( ! participantList.isEmpty() ) {
				model.addAttribute("participants",participantList);     	
				BeneficiaireWithSelectionListWrapper wrapper = new BeneficiaireWithSelectionListWrapper();
				allBeneficiaireWithSelection.clear();
				
				//Boucle de lecture des participants associés à la liste de dépense
				for (Integer cptI=0 ; cptI < participantList.size() ; cptI++) {
					boolean varChecked;
					
					//Test si le participant lu est coché dans l'interface.
					if (BeneficiaireList.contains(participantList.get(cptI))) {
						varChecked = true;
					}else {
						varChecked = false;	
					}

					//Initialisation de l'ArrayList du wrapper
					allBeneficiaireWithSelection.add(new BeneficiaireWithSelection(participantList.get(cptI).getId(), participantList.get(cptI).getName(), participantList.get(cptI).getFirstName(), varChecked ));
				}

		    	wrapper.setBeneficiaireList(allBeneficiaireWithSelection);
				
				model.addAttribute("wrapper",wrapper);
			}else {
					//Si aucun participant n'est affecté à la liste de dépense
					//on reste sur les dépenses de la liste
       				model.addAttribute("expense", expense);
       				return "redirect:/lists/" + expense.getExpenselist().getId() + "/expenses" ;
        	 	}
        }
    	
    	return "visuUpdateExpense";
    }
   
    @PostMapping("/expenses/{idExpense}/save")
    public String UpdateExpense(@ModelAttribute Expense expenseRet , @PathVariable Long idExpense, Long idPayeur, String listIdBeneficiaire)
    {											
        if (idExpense != null) {
			Participant participant = new Participant();
			List<Participant> beneficiaireList = new ArrayList();
			
        	//Traitement des bénéficiaires
        	Optional<Expense> OptionalExpense = expenseOfListService.findById(idExpense);
        	if (OptionalExpense.isPresent()) {
    			Expense expense = new Expense();
    			expense = OptionalExpense.get();		
    			expense.removeParticipants();
             
    			if (listIdBeneficiaire != null) {
    				String [] beneficiaires = listIdBeneficiaire.split(",");
    				for (int cptI =0; cptI < beneficiaires.length ;cptI++) {

        				Optional<Participant> optionalParticipant = participantService.findById(Long.parseLong(beneficiaires[cptI]));
        	        	if (optionalParticipant.isPresent()) {
        					expense.addOneParticipantBeneficiary(optionalParticipant.get());
        	        	}
    				}
    			}
        	}
        	
        	//Traitement du bénéficiairePayeur
        	Optional<Participant> optionalParticipant = participantService.findById(idPayeur);
        	participant = optionalParticipant.get();
        	visuUpdateExpenseService.addPayeurDepense(expenseRet, participant, beneficiaireList );

        }
        
        return "redirect:/lists/" + visuUpdateExpenseService.idExpenseList(idExpense) + "/expenses";
    }
    
}
