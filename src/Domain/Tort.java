package Domain;

public class Tort extends Entity{
    private String tip;

    public Tort(int id, String tip)
    {
        super(id);
        this.tip = tip;
    }

    public String toString(){
        return "Tort:{id = "+getId() + ", tip = "+tip+"}";
    }

    public String getTip(){return this.tip;}

    public void setTip(String tip){this.tip=tip;}

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}


