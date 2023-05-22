// Command.java

class Command
{
  static final int REPORT_NAMES = 0;
  static final int READY_FOR_TALON = 1;
  static final int DISCONNECT = 2;
  static final int TALON_DATA = 3;
  static final int READY_TO_PLAY = 4;
  static final int DO_PLAY = 5;
  static final int DO_WAIT = 6;
  static final int CARD_FROM_TALON = 7;
  static final int CARD_TO_PILE = 8;
  static final int NEXT_TURN = 9;
  static final int REQUEST_RESHUFFLE = 10;
  static final int DO_RESHUFFLE = 11;
  static final int RESHUFFLE_DONE = 12;
  static final int CONTINUE_AFTER_RESHUFFLE = 13;
  static final int FATAL_ERROR = 14;
  static final int TRUMP_SUITID = 15;


  static String toString(int commandTag)
  {
    switch (commandTag)
    {
      case REPORT_NAMES:
        return "REPORT_NAMES";
      case READY_FOR_TALON:
        return "READY_FOR_TALON";
      case DISCONNECT:
        return "DISCONNECT";
      case TALON_DATA:
        return "TALON_DATA";
      case READY_TO_PLAY:
        return "READY_TO_PLAY";
      case DO_PLAY:
        return "DO_PLAY";
      case DO_WAIT:
        return "DO_WAIT";
      case CARD_FROM_TALON:
        return "CARD_FROM_TALON";
      case CARD_TO_PILE:
        return "CARD_TO_PILE";
      case NEXT_TURN:
        return "NEXT_TURN";
      case REQUEST_RESHUFFLE:
        return "REQUEST_RESHUFFLE";
      case DO_RESHUFFLE:
        return "DO_RESHUFFLE";
      case RESHUFFLE_DONE:
        return "RESHUFFLE_DONE";
      case CONTINUE_AFTER_RESHUFFLE:
        return "CONTINUE_AFTER_RESHUFFLE";
      case FATAL_ERROR:
        return "FATAL_ERROR";
      case TRUMP_SUITID:
        return "TRUMP_SUITID";
    }
    return "";
  }
}
