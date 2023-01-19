public class Individual {
    int alleleOne, alleleTwo;

    public Individual(int alleleOne, int alleleTwo){
        //1 is dom
        //2 is rec
        this.alleleOne = alleleOne;
        this.alleleTwo = alleleTwo;
    }

    public int get1(){
        return alleleOne;
    }

    public int get2(){
        return alleleTwo;
    }

    public int returnRandAl(){
        int rand = (int)(Math.random()*2);
        if(rand==0){
            return alleleOne;
        }
        return alleleTwo;
    }

}
