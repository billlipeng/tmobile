package tmobile;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;



public class Tmobile {

	private static ArrayList<Name> getPrevious() throws IOException{

		BufferedReader br = new BufferedReader(new FileReader("total.txt"));
        String l1 = br.readLine();
        String l2 = br.readLine();
        br.close();

        String[] s1 = l1.split(",");
		String[] s2 = l2.split(",");

        ArrayList<Name> name = new ArrayList<>();
		for(int i=0; i<s1.length && i<s2.length; i++){
//            System.out.println(" i = " + i+ "  s1[i] = " + s1[i]+ "  s2[i] " + s2[i] );
            Name tmp = new Name();
            tmp.setId(i);
            tmp.setName(s1[i]);
            tmp.setAmount(0);
            tmp.setSum(Integer.parseInt(s2[i]));
            name.add(i, tmp);
		}

		return name;

	}
    // return 12 different numbers
	private static ArrayList<Integer> distributor(Double total){
		ArrayList<Integer> res = new ArrayList<>(12);
        for(int i=0; i<12; i++){
            res.add(i,0);
        }
        BigDecimal bd = new BigDecimal(total);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
//        System.out.println(" t = " + bd);
        int t = bd.multiply(BigDecimal.TEN).multiply(BigDecimal.TEN).intValue();
//        System.out.println(" t = " + t);
        int base = t/12;
//        System.out.println(" base = " + base);

        int sum = 0;

        for(int i=0; i<6; i++) {
            res.set(i+6, base+i+1);
            res.set(6-i-1, base-i);
            sum += 2*base + 1;
        }
        // make little adj to the value
        int r = sum - t;
        if(r >0){
            for(int i=0; i<r; i++)
                res.set(i,res.get(i)-1);
        }
        else if(r < 0){
            for(int i=0; i< -r; i++)
                res.set(11-i,res.get(i)+1);
        }
		return res;
	}


    private static ArrayList<Name> assign(ArrayList<Integer> d) throws IOException{
		ArrayList<Name> previous = getPrevious();

        for(int i=0; i<11; i++){
            for(int j=0; j<11-i; j++){
                if(previous.get(j).getSum()<previous.get(j+1).getSum()){
                    Name tmp = previous.get(j);
                    previous.set(j, previous.get(j+1));
                    previous.set(j+1, tmp);
                }
            }
        }

        for(int i=0; i<12; i++){
            previous.get(i).setAmount(d.get(i));
        }

        for(int i=0; i<11; i++){
            for(int j=0; j<11-i; j++){
                if(previous.get(j).getId()>previous.get(j+1).getId()){
                    Name tmp = previous.get(j);
                    previous.set(j, previous.get(j+1));
                    previous.set(j+1, tmp);
                }
            }
        }

        return previous;

	}

	public static void main(String[] args) throws IOException{
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter total amount for this month: ");
            double total = 0;
            try{
                 total = Double.parseDouble(br.readLine());
            }catch(NumberFormatException nfe){
                System.err.println("Invalid Format!");
            }

            System.out.println("");
            System.out.println("============================================================================ ");
            ArrayList<Name> n = assign(distributor(total));
            for (Name s : n) {
                System.out.println(" Name: " + s.getName() +
                        "  \t|\t Last: " + (double)s.getSum()/100.0 +
                        "  \t|\t Amount: " + (double)s.getAmount()/100.0+
                        "  \t|\t Total: " + (double)(s.getSum()+s.getAmount())/100.0);
            }
            System.out.println("============================================================================ ");
            System.out.println("");

            System.out.println("Would you like save the result (y/n)?");

            String s = br.readLine();

            if(s.matches("y")){
                //store new data
                try(  PrintWriter out = new PrintWriter( "total.txt" )  ){
                    for(Name name: n){
                        out.print(name.getName()+",");
                    }
                    out.println();
                    System.out.println();
                    for(Name name: n){
                        int sum = name.getAmount()+name.getSum();
                        out.print(sum+",");
                        System.out.print((double)name.getAmount()/100.0+"\t");
                    }
                    out.println();
                }
                System.out.println();
                System.out.println();
                System.out.println("Data Stored!");
            }
            else{
                System.out.println("Thanks for using!");
            }

            br.close();


        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
	
}
