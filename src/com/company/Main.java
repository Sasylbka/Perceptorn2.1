package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        List<Double> koef=new ArrayList<>();
        koef.add(0.1);
        koef.add(0.2);
        koef.add(0.3);
        koef.add(0.4);
        koef.add(0.5);
        koef.add(0.6);
        koef.add(0.7);
        koef.add(0.8);
        koef.add(0.9);
        Random random = new Random();
        double[] massA_S= new double[6];
       String charD=
                "001100" +
                "010100" +
                "010100" +
                "010100" +
                "111110";
        String charS=
                "111100" +
                "100000" +
                "100000" +
                "100000" +
                "111100";
        double[][] matrixS_A=new double [30][6];

        double[] SummAforD=new double[6];
        double[] SummAforS=new double[6];
        for(int i =0;i<30;i++){
            for(int j = 0;j<6;j++){
                matrixS_A[i][j]=koef.get(random.nextInt(9));
            }
        }
        System.out.println("Веса S и A");
        for(int i =0;i<30;i++){
            String out="";
            for(int j = 0;j<6;j++){
                out=out+" "+matrixS_A[i][j];
            }
            System.out.println(out);
        }
        System.out.println("Веса S и R");
        for (int i =0;i<6;i++){
            massA_S[i]=koef.get(random.nextInt(9));
        }
        String temp="";
        for (int i =0;i<6;i++){
            temp=temp+" "+massA_S[i];
        }
        System.out.println(temp);
        for (int j =0;j<6;j++) {
            double tempD=0;
            double tempS=0;
            for (int i = 0; i < 30; i++) {
                if(charD.charAt(i)=='1'){
                    tempD+=matrixS_A[i][j];
                }
                if(charS.charAt(i)=='1'){
                    tempS+=matrixS_A[i][j];
                }
            }
            SummAforD[j]=tempD;
            SummAforS[j]=tempS;
        }
        System.out.println("Сумма весов А при предъявлении Д "+Arrays.toString(SummAforD));
        System.out.println("Сумма весов А при предъявлении С "+Arrays.toString(SummAforS));
        double min=Double.MAX_VALUE;
        double max=Double.MIN_VALUE;
        for(int i=0;i<6;i++){
            if(SummAforD[i]<min){
                min=SummAforD[i];
            }
            if(SummAforS[i]<min){
                min=SummAforS[i];
            }
            if(SummAforD[i]>max){
                max=SummAforD[i];
            }
            if(SummAforS[i]>max){
                max=SummAforS[i];
            }
        }
        double RforS=0;
        double RforD=0;
        double bias = (max+min)/2-0.1;
        List <Integer> ActivateNeironsForD= new ArrayList<>();
        List <Integer> ActivateNeironsForS= new ArrayList<>();
        for(int i =0;i<6;i++){
            if(SummAforD[i]>bias){
                RforD+=massA_S[i];
                ActivateNeironsForD.add(i);
            }
            else{
                RforS+=massA_S[i];
                ActivateNeironsForS.add(i);
            }
        }
        System.out.println("Активированные нейроны для Д "+ActivateNeironsForD);
        System.out.println("Активированные нейроны для С "+ActivateNeironsForS);

        System.out.println("До обучения");
        System.out.println("Сумма весов R для Д:"+RforD);
        System.out.println("Сумма весов R для C:"+RforS);
        //System.out.println(Arrays.toString(massA_S));
        double biasforR=(RforD+RforS)/2;
        System.out.println("Тетта "+biasforR);
        double[] copyMassA_S = massA_S;
        double copyRforD= RforD;
        double copyRforS= RforS;
        if(RforD>=biasforR){
            System.out.println("+1 - Д");
            System.out.println("-1 - C");
        }
        else {
            System.out.println("-1 - Д");
            System.out.println("+1 - C");
            while(RforD<biasforR || RforS>=biasforR){
                if(RforD<biasforR){
                    for(int i=0;i<ActivateNeironsForD.size();i++){
                        massA_S[ActivateNeironsForD.get(i)]=massA_S[ActivateNeironsForD.get(i)]+0.1;
                    }
                }
                if(RforS>=biasforR){
                    for(int i=0;i<ActivateNeironsForS.size();i++){
                        massA_S[ActivateNeironsForS.get(i)]=massA_S[ActivateNeironsForS.get(i)]-0.1;
                    }
                }
                RforD=0;
                RforS=0;
                for(int i =0;i<6;i++){
                    if(SummAforD[i]>bias){
                        RforD+=massA_S[i];
                    }
                    else{
                        RforS+=massA_S[i];
                    }
                }
            }
            System.out.println("После альфа обучения");
            System.out.println(Arrays.toString(massA_S));
            System.out.println("Сумма весов R для Д "+RforD);
            System.out.println("Сумма весов R для С "+RforS);

            if(RforD>=biasforR){
                System.out.println("+1 - Д");
                System.out.println("-1 - Д");
            }
            double delWAct=0.1-((ActivateNeironsForS.size()*0.1)/6);
            double delWPass=(ActivateNeironsForS.size()*(-0.1))/6;
            while(copyRforD<biasforR || copyRforS>=biasforR){
                if(copyRforD<biasforR){
                    for(int i=0;i<ActivateNeironsForD.size();i++){
                        copyMassA_S[ActivateNeironsForD.get(i)]=copyMassA_S[ActivateNeironsForD.get(i)]-delWPass;
                    }
                }
                if(RforS>=biasforR){
                    for(int i=0;i<ActivateNeironsForS.size();i++){
                        copyMassA_S[ActivateNeironsForS.get(i)]=copyMassA_S[ActivateNeironsForS.get(i)]-delWAct;
                    }
                }
                copyRforD=0;
                copyRforS=0;
                for(int i =0;i<6;i++){
                    if(SummAforD[i]>bias){
                        copyRforD+=copyMassA_S[i];
                    }
                    else{
                        copyRforS+=copyMassA_S[i];
                    }
                }
            }
            System.out.println("После гамма обучения");
            System.out.println(Arrays.toString(copyMassA_S));
            System.out.println("Сумма весов R для Д "+copyRforD);
            System.out.println("Сумма весов R для C "+copyRforS);
            if(copyRforD>=biasforR){
                System.out.println("+1 - Д");
                System.out.println("-1 - Д");
            }

        }

    }
}
