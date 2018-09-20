package bepp.com.bepp.openpay.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charlie on 04/05/18.
 */

public class LuhnValidator {
    private static final int multiplicator = 2;


    public static boolean passesLuhnTest(String value) {
        char[] chars = value.toCharArray();

        List<Integer> digits = new ArrayList<Integer>();
        for ( char c : chars ) {
            if ( Character.isDigit( c ) ) {
                digits.add( c - '0' );
            }
        }
        int length = digits.size();
        int sum = 0;
        boolean even = false;
        for ( int index = length - 1; index >= 0; index-- ) {
            int digit = digits.get( index );
            if ( even ) {
                digit *= multiplicator;
            }
            if ( digit > 9 ) {
                digit = digit / 10 + digit % 10;
            }
            sum += digit;
            even = !even;
        }
        return sum % 10 == 0;
    }
}