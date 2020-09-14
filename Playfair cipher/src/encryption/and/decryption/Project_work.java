
package encryption.and.decryption;
import java.util.Arrays;
import java.util.Scanner;

public class Project_work {
    static char board[][] = new char[5][5];
    static int k = 0;

    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the keyword: ");
        String keyword = sc.nextLine();
        System.out.print("Enter the text: ");
        String text = sc.nextLine(); 
        System.out.print("Choose option (encrypt/decrypt): ");
        String option = sc.nextLine(); 
        String enc = "encrypt";
        String dec = "decrypt";
        char[] newkey = relateTexts(deleteRepeat(keyword)); 
        if(option.equals(enc)){
            char[] newtext = textPreparation(text);
            encode(newtext, newkey);
        }
        else{
            if(option.equals(dec)){
            decode(codePreparation(text),newkey);
            }        
            else{System.out.println("Try again!");}
        }
    }


    public static char[] deleteRepeat(String word){ //delete all repeats in keyword
        char[] key = word.toCharArray();
        for(int i=0; i<key.length; i++){
            for(int j=i+1;j<key.length; j++){
                if(key[i]==key[j]){
                    key[j]=' ';
                }
            }
        }
        String letter = Arrays.toString(key);
        String let = letter.toUpperCase().replaceAll("[^A-Z]", "");
        char[] keys = let.toCharArray();
        return keys;
    }
    
    public static char[] relateTexts(char[] keyword){               //Ready keyword connect with other alphabet character
        char[] alpha = "ABCDEFGHIJKLMNOPRSTUVWXYZ".toCharArray();   //alphabet without Q!
        char[] word = new char[25];
        for(int i=0; i<keyword.length; i++){
            for(int j=0; j<25; j++){
                if(keyword[i]==alpha[j]){
                    alpha[j]=' ';
                }        
            }
        }
        String letter = Arrays.toString(alpha);
        String let = letter.toUpperCase().replaceAll("[^A-Z]", "");
        char[] key = let.toCharArray();       
        for(int i=0; i<25; i++){
            if(i<keyword.length){
                word[i]=keyword[i];
            }
            else{
                word[i]=key[k];
            k++;}
        }
        k=0;
        return word;
    }
    
    public static char[] textPreparation(String text){/*for digraph with equal letter change second character to 'X' 
        and if keyword consist odd number of character creat new array with extra 1 length where element is 'Z' */
        String word = text.toUpperCase().replaceAll("[^A-Z]", "");
        char[] texts = word.toCharArray();
        if(texts.length%2==1){
            char[] extra = new char[texts.length+1];
            System.arraycopy(texts, 0, extra, 0, texts.length);
            extra[texts.length]='Z';
            for(int i=0; i<extra.length; i++){
                    if(i%2==0 && extra[i]==extra[i+1]){extra[i+1]='X';}
            }
        return extra;}
        else{
            for(int i=0; i<texts.length; i++){
                    if(i%2==0 && texts[i]==texts[i+1]){texts[i+1]='X';}
            }
        return texts;
        }        
    }
    
    public static char[] codePreparation(String code){
        String ncode = code.toUpperCase().replaceAll("[^A-Z]", "");
        char[] text = ncode.toCharArray();   
        return text;
     }
    
    public static void decode(char[] text/*text to decode*/, char[] key){
        String let = String.valueOf(key);
        char code[] = new char[text.length]; 
        System.out.println(let);
        for(int i=0; i<text.length; i++){
            if(i%2==0){
                int loc1 = let.indexOf(text[i]);                
                int loc2 = let.indexOf(text[i+1]);
                if(loc1>=0 && loc1<=4 && loc2>=0 && loc2<=4 || loc1>=5 && loc1<=9 && loc2>=5 && loc2<=9|| loc1>=10 && loc1<=14 && loc2>=10 && loc2<=14||
                loc1>=15 && loc1<=19 && loc2>=15 && loc2<=19|| loc1>=20 && loc1<=24 && loc2>=20 && loc2<=24){
                    for(int n=0; n<5; n++){
                        if(loc2==5*n){
                            code[i]=key[loc1-1];
                            code[i+1]=key[5*n+4];
                            break;
                        }
                        else{
                            if(loc1==5*n){
                                code[i]=key[5*n+4];
                                code[i+1]=key[loc2-1];                                
                                break;
                            }
                            else{
                                if(loc1!=5*n && loc2!=5*n){
                                code[i]=key[loc1-1];
                                code[i+1]=key[loc2-1];
                                }                            
                            }
                        }
                    }
                }
                for(int p=0; p<5;p++){
                    if(loc1%5==p){                  //p is column where loc1
                        int index = (loc1-p)/5;     //location of row of loc1
                        int index2 = (loc2-p)/5;    //location of row of loc2
                        for(int h=0; h<5; h++){
                            if(loc2%5==p){          //if loc2 located in same column                                    
                                    if(loc2==p){
                                        code[i]=key[5*index+p-5];
                                        code[i+1]=key[20+p];
                                        break;
                                    }
                                    else{
                                        if(loc1==p){
                                            code[i]=key[20+p];
                                            code[i+1]=key[5*index2-5+p];
                                            break;
                                        }
                                        else{
                                            if(loc1!=p && loc2!=p){
                                            code[i]=key[5*index-5+p];
                                            code[i+1]=key[5*index2-5+p];
                                            }
                                        }
                                    }
                            }
                            else{
                                int e = loc2%5;
                                index2 = (loc2-e)/5;
                                int rate = p-e;
                                int atach = index-index2;
                                if(rate!=0 && atach!=0){
                                        code[i]=key[5*index+p-rate];
                                        code[i+1]=key[5*index2+e+rate];
                                        break;
                                }
                            }
                        }
                    }
                }
            }
        } 
        System.out.print("Your decoded message is: ");
        for(int i=0; i<code.length; i++){System.out.print(code[i]);if(i%2==1){System.out.print(" ");}}
        System.out.println();
    }
    
    public static void encode(char[] text/*text to encode*/, char[] key){
        String let = String.valueOf(key);
        char code[] = new char[text.length]; 
        for(int i=0; i<text.length; i++){
            if(i%2==0){
                int loc1 = let.indexOf(text[i]);                
                int loc2 = let.indexOf(text[i+1]);
                if(loc1>=0 && loc1<=4 && loc2>=0 && loc2<=4 || loc1>=5 && loc1<=9 && loc2>=5 && loc2<=9|| loc1>=10 && loc1<=14 && loc2>=10 && loc2<=14||
                loc1>=15 && loc1<=19 && loc2>=15 && loc2<=19|| loc1>=20 && loc1<=24 && loc2>=20 && loc2<=24){
                    for(int n=1; n<6; n++){
                        if(loc2==5*n-1){
                            code[i]=key[loc1+1];
                            code[i+1]=key[4*n+n-5];
                            break;
                        }
                        else{
                            if(loc1==5*n-1){
                            code[i]=key[4*n+n-5];
                            code[i+1]=key[loc2+1];
                            break;
                            }
                            else{
                                if(loc1!=5*n-1 && loc2!=5*n-1){
                                code[i]=key[loc1+1];
                                code[i+1]=key[loc2+1];
                                }
                            }
                        }
                    }
                }
                for(int p=0; p<5;p++){
                    if(loc1%5==p){                  //p is column where loc1
                        int index = (loc1-p)/5;     //location of row of loc1
                        int index2 = (loc2-p)/5;
                        for(int h=0; h<5; h++){
                            if(loc2%5==p){          //5*h-5+p is location loc2 in column
                                    if(loc2==20+p){
                                        code[i]=key[5*index+5+p];
                                        code[i+1]=key[p];
                                        break;
                                    }
                                    else{
                                        if(loc1==20+p){
                                            code[i]=key[p];
                                            code[i+1]=key[5*index2+5+p];
                                            break;
                                        }
                                        else{
                                            code[i]=key[5*index+5+p];
                                            code[i+1]=key[5*index2+5+p];
                                        }
                                    }
                            }
                            else{
                                int e = loc2%5;
                                index2 = (loc2-e)/5;
                                int rate = p-e;
                                int atach = index-index2;
                                if(rate!=0 && atach!=0){
                                    code[i]=key[5*index+p-rate];
                                    code[i+1]=key[5*index2+e+rate];
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        } 
        System.out.print("Your encrypted message is: ");
        for(int i=0; i<code.length; i++){
            System.out.print(code[i]);if(i%2==1){System.out.print(" ");}}
        System.out.println();
    }
}
