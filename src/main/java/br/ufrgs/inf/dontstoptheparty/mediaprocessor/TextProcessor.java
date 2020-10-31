package br.ufrgs.inf.dontstoptheparty.mediaprocessor;

import br.ufrgs.inf.dontstoptheparty.enumerator.Instrument;
import br.ufrgs.inf.dontstoptheparty.enumerator.Note;
import br.ufrgs.inf.dontstoptheparty.token.NoteToken;
import br.ufrgs.inf.dontstoptheparty.token.Token;
import br.ufrgs.inf.dontstoptheparty.token.actions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextProcessor implements MediaProcessorInterface<String> {
    private Map<Character, Token> charToTokenMap = new HashMap<Character, Token>() {{
        //NoteTokens
        put('A', new NoteToken(Note.LA));
        put('B', new NoteToken(Note.SI));
        put('C', new NoteToken(Note.DO));
        put('D', new NoteToken(Note.RE));
        put('E', new NoteToken(Note.MI));
        put('F', new NoteToken(Note.FA));
        put('G', new NoteToken(Note.SOL));

        //ActionTokens
        put('!', new ChangeInstrumentActionToken(Instrument.AGOGO));
        put('I', new ChangeInstrumentActionToken(Instrument.HARPSICHORD));
        put('i', new ChangeInstrumentActionToken(Instrument.HARPSICHORD));
        put('U', new ChangeInstrumentActionToken(Instrument.HARPSICHORD));
        put('u', new ChangeInstrumentActionToken(Instrument.HARPSICHORD));
        put('\n', new ChangeInstrumentActionToken(Instrument.TUBULAR_BELLS));
        put(';', new ChangeInstrumentActionToken(Instrument.PAN_FLUTE));
        put(',', new ChangeInstrumentActionToken(Instrument.CHURCH_ORGAN));

        put(' ', new DoubleVolumeActionToken());
        put('?', new IncreaseOctaveActionToken());

        put('1', new IncreaseInstrumentActionToken(1));
        put('2', new IncreaseInstrumentActionToken(2));
        put('3', new IncreaseInstrumentActionToken(3));
        put('4', new IncreaseInstrumentActionToken(4));
        put('5', new IncreaseInstrumentActionToken(5));
        put('6', new IncreaseInstrumentActionToken(6));
        put('7', new IncreaseInstrumentActionToken(7));
        put('8', new IncreaseInstrumentActionToken(8));
        put('9', new IncreaseInstrumentActionToken(9));
    }};


    @Override
    public List<Token> convert(String entry) {
        final List<Token> tokenList = new ArrayList<>();
        char previousChar = entry.charAt(0);

        for (char ch : entry.toCharArray()) {
            Token aux = charToTokenMap.get(ch);
            if (aux != null) {
                tokenList.add(charToTokenMap.get(ch));
            }
            else{
                switch (previousChar){
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'E':
                case 'F':
                case 'G':
                tokenList.add(charToTokenMap.get(ch));
                break;

                default:
                tokenList.add(new SilenceActionToken());
                }
            }
            previousChar = ch;
        }
        return tokenList;
    }
}

