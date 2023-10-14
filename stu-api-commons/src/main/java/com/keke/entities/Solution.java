package com.keke.entities;

class Solution {
    public String longestPalindrome(String s) {
        String result="";
        int flag = -1;
        if(s.length()==1){
            return s;
        }
        for(int i=0;i<s.length()-1;i++){
            for(int j=i+1;j<s.length();j++){
                System.out.println("i:"+i+"j:"+j+"..."+s.substring(i,j+1));
                if(s.substring(i,j+1).equalsIgnoreCase(reverseString(s.substring(i,j+1)))){
                    if(flag < s.substring(i,j+1).length()){
                        System.out.println("yes!");
                        result = s.substring(i,j+1);
                        flag = result.length();
                    }
                }
            }
        }
        if(flag == -1) return String.valueOf(s.charAt(0));
        return result;
    }

    public String reverseString(String str) {
        System.out.println("str:"+str);
        char[] s = str.toCharArray();
        for(int i=0;i<s.length/2;i++){
            char temp=s[i];
            s[i] = s[s.length-1-i];
            s[s.length-1-i] = temp;
        }
        System.out.println("反转之后："+new String(s));
        return new String(s);
    }

    public static void main(String[] args) {
        String result = new Solution().longestPalindrome("kyyrjtdplseovzwjkykrjwhxquwxsfsorjiumvxjhjmgeueafubtonhlerrgsgohfosqssmizcuqryqomsipovhhodpfyudtusjhonlqabhxfahfcjqxyckycstcqwxvicwkjeuboerkmjshfgiglceycmycadpnvoeaurqatesivajoqdilynbcihnidbizwkuaoegmytopzdmvvoewvhebqzskseeubnretjgnmyjwwgcooytfojeuzcuyhsznbcaiqpwcyusyyywqmmvqzvvceylnuwcbxybhqpvjumzomnabrjgcfaabqmiotlfojnyuolostmtacbwmwlqdfkbfikusuqtupdwdrjwqmuudbcvtpieiwteqbeyfyqejglmxofdjksqmzeugwvuniaxdrunyunnqpbnfbgqemvamaxuhjbyzqmhalrprhnindrkbopwbwsjeqrmyqipnqvjqzpjalqyfvaavyhytetllzupxjwozdfpmjhjlrnitnjgapzrakcqahaqetwllaaiadalmxgvpawqpgecojxfvcgxsbrldktufdrogkogbltcezflyctklpqrjymqzyzmtlssnavzcquytcskcnjzzrytsvawkavzboncxlhqfiofuohehaygxidxsofhmhzygklliovnwqbwwiiyarxtoihvjkdrzqsnmhdtdlpckuayhtfyirnhkrhbrwkdymjrjklonyggqnxhfvtkqxoicakzsxmgczpwhpkzcntkcwhkdkxvfnjbvjjoumczjyvdgkfukfuldolqnauvoyhoheoqvpwoisniv");
        System.out.println("result:"+result);
    }

}
