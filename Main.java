// Sam Heidler - srh220000 - project 3 - BST/integral calculator

import java.util.*;
import java.io.*;

public class Main {

    static int upper;
    static int lower;
    


    /*******************************************************
     *                        main                         *
     *    creates Scanner for user input and file input,   *
     *     tests file, passes it to getFileData, calls     *
     *   subsequent methods to perform logic on file data  *
     *******************************************************/
    public static void main(String[] args) throws IOException 
    {
        Scanner scnr = new Scanner(System.in); // gets input from keyboard

        //System.out.print("Enter the file to open: "); // "testFile.txt" in my program - // DELETE
        
        // get input file name from user
        String filename = scnr.nextLine(); // read in file name to open
        File inFS = new File(filename); // create file object
        Scanner inputFile = new Scanner(inFS); // scanner for input from file to console

        if (inFS.exists()) // if the file is able to be opened, use it
        {
            ArrayList<Node<Term>> list = new ArrayList<>();
            String expression;
            BinTree<Term> tree = new BinTree<>();
            
            
            euclideanAlgo(2, 0);

            while(inputFile.hasNext())
            {
                expression = getFileData(inputFile);
                if(expression == null)
                {
                    continue;
                }
                tree = processData(expression);
                list = tree.reverseInOrder();
/*                 for(Node<Term> term: list)
                {
                    System.out.println(term.getData().toString());
                }
*/
                System.out.println(calcIntegral(list));
                


            }
            
        }
        else
        {
            System.out.println("The file " + filename + " could not be found.");
        }

        scnr.close();

    } // end of main()


    /*******************************************************
     *                    euclideanAlgo                    *
     *   finds the greatest common factor of two numbers   *
     *******************************************************/
    public static int euclideanAlgo(int num1, int num2) {
        // euclidean algorithm to help simplify fractions
        int r = 1;
        if(num1 == 0 || num2 == 0)
        {
            return 0;
        }
        while(r != 0 && r > 0)
        {
            r = num1 % num2;
            if (r != 0)
            {
                num1 = num2;
                num2 = r;      // num2 holds the gcf
            }
               
        }
        return num2;
    }

    /*******************************************************
     *                       calcIntegral                  *
     *   This function evaluates definite and indefinite   *
     *           integrals for each polynomial             *
     *                  term in an arrayList               *
     *******************************************************/
    public static String calcIntegral(ArrayList<Node<Term>> list) {
        String exp = "";
        Node<Term> term;
        int expon;
        int coeff;
        int numer;
        int denom;
        int gcf;

        double total = 0.0;
        //double upperTotal = 0.0;

        if(!list.get(0).getData().isDefinite() || list.get(0).getData().isDefinite())     // the terms are indefinite
        {
            for(int x = 0; x < list.size(); x++)     //Node<Term> term: list)
            {
                term  = list.get(x);    // holds current polynomial term in arrayList

                coeff = term.getData().getCoefficient();
                expon = term.getData().getExponent();
               // System.out.println("coeff: " + coeff);

                if(!term.getData().isConstant())    // if the term is not a constant, then evaluate variable
                {
                    numer = coeff;
                    denom = expon + 1;
                    expon += 1;

                    if(numer != 0 && denom != 0)            // run euclidean algo
                    {
                        if(Math.abs(numer) > Math.abs(denom))
                        {
                            gcf = euclideanAlgo(numer, denom);
                        }
                        else
                        {
                            gcf = euclideanAlgo(denom, numer);
                        }
                        
                        numer /= gcf;       // divide numerator and denominator by their greatest common factor (gcf)
                        denom /= gcf;       // to reduce to simpliest terms
                        //System.out.println("\nx: " + x + "\nDenom: " + denom + "\nNumer: "+ numer +"\nGCF: " + gcf + "\nexpon: " + expon);    // used for testing
                    }
                    

                    if(list.get(0).getData().isDefinite())      // evaluate value of definite integral
                    {                        
                        //System.out.println("term: " + list.get(x).getData().toString());
                        if(expon != 1)   // polynomial raised to a power other than 1
                        {
                            if(denom != 0)
                            {
                                total += (((double)numer/(double)denom)*(Math.pow((double)upper, expon)) - ((double)numer/(double)denom)*(Math.pow((double)lower, expon)));
                                //upperTotal = ((double)numer/(double)denom)*(Math.pow((double)upper, expon));
                            }
                            else 
                            {
                                total += 0.0;
                            }
                            
                        }
                        else 
                        {
                            if(denom != 1)
                            {
                                if(denom != 0)
                                {
                                    total += (((double)numer/(double)denom)*upper - ((double)numer/(double)denom)*lower);
                                    //upperTotal = ((double)numer/(double)denom)*upper;
                                }
                                else 
                                {
                                    total += 0.0;
                                }                             
                            }
                            else
                            {
                                total += (((double)numer)*upper - ((double)numer)*lower);
                                //upperTotal = ((double)numer)*upper;
                            }
                            
                        }
                        //System.out.println("total: " + total);
                    }


                    
                       
                    if(numer == 0 || denom == 0)
                    {
                        exp += "0";
                    }
                    else if(numer < 0 || denom < 0)    // the term has a negative
                    {
                        if(numer > 0)  // numerator is positive so the denominator is negative
                        {
                            
                            if(x == 0)  // first term being displayed
                            {
                                if(denom != -1)     // -1 is not the denominator so display fraction
                                {
                                    exp += "(" + (numer*-1) +"/" + (denom*-1) + ")x^" + expon;
                                }
                                else if(denom == -1)
                                {
                                    //System.out.println("negative -1 denom");
                                    exp +=  numer + "x^" + expon;
                                }
                                

                            }
                            else    // x > 0
                            {
                                if(denom != -1)
                                {
                                    exp += " - (" + (numer) +"/" + (denom*-1) + ")x^" + expon;
                                }
                                else if(numer != 1)
                                {
                                    exp += " - " + numer + "x^" + expon;
                                }
                                else
                                {
                                    exp += " - x^" + expon;
                                }
                                
                            }
                            
                        }
                        else if(denom > 0)   
                        {
                            if(x == 0)  // first term being displayed
                            {
                                if(denom != 1)     // 1 is not the denominator so display fraction
                                {
                                    exp += " - (" + (numer*-1) +"/" + denom + ")x^" + expon;
                                }
                                else if(denom == 1)
                                {
                                    //System.out.println("negative -1 denom");
                                    exp += " - " + (numer*-1) + "x^" + expon;
                                }
                                
                                

                            }



                            else if(numer == -1 && denom == 1)
                            {
                                exp +=  numer + "x^" + expon;
                            }
                            else if(numer != 1 && numer != -1 && denom == 1)
                            {
                                exp += " - " + (numer*-1) + "x^" + expon;
                            }
                        }
                        else if(numer != 1 && denom != -1)    // denom = 1 so don't display fraction
                        {
                            if(numer % denom != 0)
                            {
                                exp +=  " (" + numer +"/" + denom + ")x^" + expon;
                            }
                            else
                            {
                               // System.out.println("1 negative -1 denom");
                                exp +=  " - " + (numer * -1) + "x^" + expon;
                            }
                            
                        }
                        
                    }



                    else if(x == 0 && numer > 0 && denom > 0)
                    {
                        if(denom != 1)     // 1 is not the denominator so display fraction
                        {
                            exp += "(" + numer +"/" + denom + ")x^" + expon;
                        }
                        else if(denom == 1 && numer != 1)
                        {
                            exp +=  numer + "x^" + expon;
                        }
                        else if(numer == 1 && denom == 1)
                        {
                            exp +=  "x^" + expon;
                        }
                    }

                    else if(x > 0 && coeff > 0)            // the term is positive and not the first term, so prepend a "+" before each following term
                    {
                        
                        exp += " + ";
                        if(denom != 1 && denom != -1)
                        {
                            exp += "(" + numer +"/" + denom + ")x^" + expon;
                        }
                        else if(numer != 1)    // denom = 1 so don't display fraction, but include coefficient
                        {
                            exp +=  numer + "x^" + expon;
                        }
                        else    // numerator and denominator both = 1 so don't include coefficient
                        {
                            exp += "x^" + expon;
                        }
                    }
                    else if(x > 0 && list.get(0).getData().getCoefficient() != 0)        // x == 0, first term
                    {
                        exp += " + ";
                        if(denom != 1)
                        {
                            exp += "(" + numer +"/" + denom + ")x^" + expon;
                        }
                        else if(numer != 1)    // denom = 1 so don't display fraction
                        {
                            exp +=  numer + "x^" + expon;
                        }
                        else    // numerator and denominator both = 1 so don't include coefficient
                        {
                            exp += "x^" + expon;
                        }
                    }
                    else if(list.get(0).getData().getCoefficient() == 0)
                    {
                        if(x > 1)
                        {
                            exp += " + ";
                        }
                        if(denom != 1)
                        {
                            exp += "(" + numer +"/" + denom + ")x^" + expon;
                        }
                        else if(numer != 1)    // denom = 1 so don't display fraction
                        {
                            exp +=  numer + "x^" + expon;
                        }
                        else    // numerator and denominator both = 1 so don't include coefficient
                        {
                            exp += "x^" + expon;
                        }
                    }
                    
                    
                }


                else        // term is a constant so it = number * x
                {
                    if(coeff != 0)
                    {
                        total += (((double)coeff)*upper - ((double)coeff)*lower);
                    }
                    

                    //System.out.println("upper total: " + upperTotal);
                    if(x != 0)
                    {
                        if(coeff > 0) // prepend plus sign before positive, trailing constants
                        {
                            exp += " + ";
                            if(coeff != 1)
                            {
                                exp += coeff + "x";
                            }
                            else
                            {
                                exp += "x";
                            }
                        }
                        else if(coeff < 0)    // trailing negative constant
                        {
                            if(coeff == -1)
                            {
                                exp += " - x";
                            }
                            else
                            {
                                exp += " - " + (coeff*-1) + "x";
                            }       
                        }
                    }
                    else    // constant is first term
                    {
                        if(coeff > 0) // display positive first term constant
                        {
                            if(coeff != 1)
                            {
                                exp += coeff + "x";
                            }
                            else
                            {
                                exp += "x";
                            }
                        }
                        else if(coeff < 0)    // first term is negative constant
                        {
                            if(coeff == -1)
                            {
                                exp += "-x";
                            }
                            else
                            {
                                exp += coeff + "x";
                            }       
                        }
                        else if(coeff == 0 && list.get(x+1) == null)   // first term is 0
                        {
                            exp += "0";
                        }
                    }
                    
                    
                    
                }
            }

            // add conditional check to split between indefinite / definite integral display (" + C" or ",#|# = #.###")

            if(!list.get(0).getData().isDefinite())
            {
                exp += " + C";  // add constant of integration
            }
            else
            {
                if(Math.abs(total) < 0.000001)
                {
                    exp += ", " + lower + "|" + upper + " = 0";
                }
                else
                {
                    exp += ", " + lower + "|" + upper + " = " + String.format("%.3f", total);
                }
            }
           
        }
        return exp;
    }


    /*******************************************************
     *                       processData                   *
     * This function parses each line from the input file  *
     *    and creates term objects based on that data      *
     *  and stores those term objects in nodes in the BST  *
     *******************************************************/
    public static BinTree<Term> processData(String exp) {
        // parse data
        // for each term - 
        // store the term in a node
        // insert that node in the BST
        ArrayList<Term> termList = new ArrayList<>();

        BinTree<Term> bst = new BinTree<>();

        int exponent;
        int coefficient;
        
        boolean constant;
        boolean definite;
        Term cur;

        int i;
        String parseSpace;
        String replaced;
        String coeff;
        String[] x;     // space split: first element contains integral info
        String[] y;     // + split: multiple terms
        String[] z;     // ^ split: exponent
        String[] f;


        i = 1;
        parseSpace = "";

        //System.out.println(exp);

        x = exp.split(" ");      // split the input line from the file by spaces
                                        // the first element in this will be the integral data
        if(x[0].length() > 1)
        {
            definite = true;
            //System.out.println("definite integral");
            f = x[0].split("\\|");
        
            lower = Integer.parseInt(f[0]);
            upper = Integer.parseInt(f[1]);
            //System.out.println("upper " + upper + " lower " + lower);
        }
        else
        {
            definite = false;
            //System.out.println("indefinite integral");
        }


        while(i < x.length - 1)     // creates a string that holds only the polynomial expression (no spaces)
        {
            parseSpace += x[i];
            i++;
        }
        //System.out.println("parse by space: " + parseSpace);
       
        replaced = parseSpace.replace("--", "+");   // replace double negatives with a postive "--" -> "+"
        replaced = replaced.replace("-", "+-");   // replace "-" with "+-" so that expression can be split and terms can retain their negative values
       
        //System.out.println("replaced: " + replaced);


        y = replaced.split("\\+");      // split the expression into terms

        for(int a=0; a<y.length; a++)
        {
           //System.out.println("term: " + y[a]);
            if(y[a].contains("^"))          // the term is a polynomial with an exponent that != 1
            {
                constant = false;

                z = y[a].split("\\^");
                if(z.length < 2)                // the term has a negative exponent
                {
                    exponent = Integer.parseInt(y[a+1]);
                    //System.out.println("negative exponent " + exponent);
                    a++;

                    coeff = z[0].replace("x", "");
                    if(coeff.equals(""))
                    {
                        coefficient = 1;
                    }
                    else if(coeff.equals("-"))
                    {
                        coefficient = -1;
                    }
                    else
                    {
                        coefficient = Integer.parseInt(coeff);
                    }                 
                    //System.out.println("coefficient: " + coefficient);

                    Term obj = new Term(coefficient, exponent, definite, constant);
                    termList.add(obj);

                }
                else if(z.length == 2)          // the term has a positive exponent stored in z[1]
                {
                    exponent = Integer.parseInt(z[1]);
                    //System.out.println("exponent " + exponent);
                    coeff = z[0].replace("x", "");
                    if(coeff.equals(""))
                    {
                        coefficient = 1;
                    }
                    else if(coeff.equals("-"))
                    {
                        coefficient = -1;
                    }
                    else
                    {
                        coefficient = Integer.parseInt(coeff);
                    }                 
                    //System.out.println("coefficient: " + coefficient);

                    Term obj = new Term(coefficient, exponent, definite, constant);
                    termList.add(obj);
                }
            }

            else if(y[a].contains("x"))         // the term is a variable with exponent = 1
            {
                constant = false;

                coeff = y[a].replace("x", "");
                if(coeff.equals(""))
                {
                    coefficient = 1;
                }
                else if(coeff.equals("-"))
                {
                    coefficient = -1;
                }
                else
                {
                    coefficient = Integer.parseInt(coeff);
                }      
                
                exponent = 1;
                //System.out.println("coefficient: " + coefficient);
                //System.out.println("exponent " + exponent);

                Term obj = new Term(coefficient, exponent, definite, constant);
                termList.add(obj);
            }

            else if(!y[a].contains("x"))    // the term is a constant
            {
                constant = true;
                exponent = 0;
                if(y[a] != null && !y[a].equals(""))
                {
                    if(y[a].equals("-"))
                    {
                        coefficient = -1;
                    }
                    else
                    {
                        coefficient = Integer.parseInt(y[a]);
                    }
                    
                } 
                else
                {
                    coefficient = 0;
                }
                
//System.out.println("constant: " + coefficient);

                Term obj = new Term(coefficient, exponent, definite, constant);
                termList.add(obj);
            }
            //System.out.println("definite?  " + definite);
            //System.out.println(" ");
        }

        //System.out.println(termList.toString());

        // combine like terms in arrayList of terms
        for(int c = 0; c<termList.size(); c++)
        {
            cur = termList.get(c);
            for(int b = c+1; b<termList.size(); b++)
            {
                if(termList.get(b).compareTo(cur) == 0)
                {
                    //System.out.println("found like term");
                    cur.setCoefficient(termList.get(b).getCoefficient() + cur.getCoefficient());
                    termList.remove(b);
                    b--;
                    //System.out.println(termList.toString());                   
                }
            }
        }
        //System.out.println(termList.toString());
        for(Term j: termList)
        {
            bst.insert(j);
        }

        
        return bst;



    }


    /*******************************************************
     *                       getFileData                   *
     * This function loops to get input for the user file  *
     *******************************************************/
    public static String getFileData(Scanner inFS) {

        String currWord; // holds the current line of input from the file

        if(inFS.hasNext())
        {
            currWord = inFS.nextLine(); // gets line of input from file as a string
        }
        else
        {
            currWord = null;
        }
        if(currWord.equals(""))
        {
            currWord = null;
        }

        return currWord;

    } // end of getFileData




} // end of Main class
