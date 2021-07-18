package com.natixis.tricount.dto;

import java.util.ArrayList;

public class BeneficiaireWithSelectionListWrapper {
    private ArrayList<BeneficiaireWithSelection> beneficiaireList;

    public ArrayList<BeneficiaireWithSelection> getBeneficiaireList() {
        return beneficiaireList;
    }

    public void setBeneficiaireList(ArrayList<BeneficiaireWithSelection> beneficiaires) {
        this.beneficiaireList = beneficiaires;
    }
}
