public class Term implements Comparable<Term> {
	private int coefficient;
    private int exponent;
    private boolean isDefinite;
    private boolean isConstant;

    // overloaded constructor
    public Term(int coefficient, int exponent, boolean isDefinite, boolean isConstant) 
    {
        this.coefficient = coefficient;
        this.exponent = exponent;
        this.isDefinite = isDefinite;
        this.isConstant = isConstant;
    }

    // getters
    public int getCoefficient() {return coefficient;}
    public int getExponent() {return exponent;}
    public boolean isDefinite() {return isDefinite;}
    public boolean isConstant() {return isConstant;}
    
    // setters
    public void setCoefficient(int coefficient) {this.coefficient = coefficient;}
    public void setExponent(int exponent) {this.exponent = exponent;}
    public void setDefinite(boolean definite) {isDefinite = definite;}
    public void setConstant(boolean constant) {isConstant = constant;}

    @Override                           // overridden compare function so that terms can be compared generically
    public int compareTo(Term obj2)
    {
        if(exponent > obj2.getExponent())
        {
            return 1;
        }
        else if(exponent < obj2.getExponent())
        {
            return -1;
        }
        return 0; // they're equal
    }

    @Override
    public String toString()        // display contents of a term
    {
        if(!isConstant)
        {return coefficient + "x^" + exponent;}
        else
        {return coefficient + "";}
    }
}
