package com.natixis.tricount.mail;


import com.natixis.tricount.dto.EmailAddress;
import com.natixis.tricount.entity.ExpenseList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class Mail
{
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(EmailAddress emailAddress, ExpenseList expenseList) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(emailAddress.getEmailAddress());
        msg.setFrom("tricountwildschool@outlook.fr");

        msg.setSubject("[TriCount.com] - Traitement de l'équilibrage " + expenseList.getName());
        msg.setText("Bonjour, \n Pour information, l'équilibrage de la liste de dépense "+ expenseList.getName() +" a été lancé avec succès. \n \n L'équipe TriCount.com");
        javaMailSender.send(msg);
    }

}
