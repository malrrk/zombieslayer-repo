// Command.java

interface Command
{
  int GAME_INIT = 0;
  int REPORT_SHOW = 1;
  int REPORT_HIDE = 2;
  int REPORT_SCORE = 3;
  int REQUEST_GAME_ENTRY = 4;
  int TOO_MANY_PLAYERS = 5;
  int PLAYER_NAMES = 6;
  int WAIT_REMOTE_CONNECT = 7;
  int REMOTE_MOVE = 8;
  int LOCAL_MOVE = 9;
  int DISCONNECTED = 10;
  int GAME_OVER = 11;
}
