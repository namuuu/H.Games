package fr.namu.hg.scoreboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.namu.hg.MainHG;
import fr.namu.hg.PlayerHG;
import fr.namu.hg.enumhg.GamesHG;
import fr.namu.hg.enumhg.StateHG;
import fr.namu.hg.games.cts.CTSEnum;

public class ScoreBoardHG {
	final MainHG main;
	
	private int timer = 0;
	
	private String top1 = null;
	private String top2 = null;
	private String top3 = null;
	
	public ScoreBoardHG(MainHG main) {
	    this.main = main;
	  }
	
	  public void updateScoreBoardLobby(FastBoard board) {
		    String[] score = { 
		            "",
		            "§fJoueurs : §b",
		            "§fConfiguration : §b",
		            "",
		            "",
		            "Joueurs manquants : ",
		            "",
		            "§7www.h-party.net" 
		    };
		    
		    score[1] = score[1] + this.main.GeneralUtils.GetNbPlayer() + "§f/§b" + this.main.LobbyUtils.getMaxPlayers();
		    score[2] = score[2] + this.main.LobbyUtils.getConfigName();
		    if(this.main.LobbyUtils.getAutoStartTimer() == -1) {
		    	score[5] = score[5] + "§b" + this.main.LobbyUtils.getRequiredLeft();
		    } else {
		    	score[5] = "Lancement dans : §b" + this.main.LobbyUtils.getAutoStartTimer() + "s";
		    }		    		    
		    for (int i = 0; i < score.length; i++) {
		      StringBuilder sb = new StringBuilder();
		      sb.append(score[i]);
		      if (sb.length() > 30)
		        sb.delete(29, sb.length() - 1); 
		      score[i] = sb.toString();
		    } 
		    board.updateLines(score);
		    board.updateTitle("§l§3H.GAMES");
		  }
	  
	  public void updateScoreBoardIntergame(FastBoard board) {
		  String[] score = { 
				  "",
		          "§fJoueurs : §b",
		          "",
		          "",
		          "",
		          "",
		          "",
		          "§fTa place : §b",
		          "",
		          "§fMini-Jeux : §b",
		          "" 
		  };
		    PlayerHG playerhg = this.main.playerhg.get(board.getPlayer().getUniqueId());
		    top1 = null;
		    top2 = null;
		    top3 = null;
		  
		    score[1] = score[1] + this.main.GeneralUtils.GetNbPlayer() + "§f/§b" + this.main.LobbyUtils.getMaxPlayers();
		    List<UUID> players = new ArrayList<>(this.main.playerhg.keySet());
			for (Integer ind = 0; ind < players.size(); ind++) {
				Player player = Bukkit.getPlayer(players.get(ind));
				PlayerHG phg = this.main.playerhg.get(players.get(ind));
				if(this.main.ScoreUtils.getPlace(player) == 1 && top1 == null) {
					score[3] = "§b#1 - §f" + player.getName() + " §7(" + phg.getStarDust() + "✦)";
					top1 = player.getName();
				} else {
					if(this.main.ScoreUtils.getPlace(player) == 1 && top2 == null) {
						score[4] = "§b#1 - §f"+ player.getName() + " §7(" + phg.getStarDust() + "✦)";
						top2 = player.getName();
					} else {
						if(this.main.ScoreUtils.getPlace(player) == 1 && top3 == null) {
							score[5] = "§b#1 - §f" + player.getName() + " §7(" + phg.getStarDust() + "✦)";
							top3 = player.getName();
						}
					}
				}
				if(this.main.ScoreUtils.getPlace(player) == 2 && top2 == null) {
					score[4] = "§3#2 - §f" + player.getName() + " §7(" + phg.getStarDust() + "✦)";
					top2 = player.getName();
				} else {
					if(this.main.ScoreUtils.getPlace(player) == 2 && top3 == null) {
						score[5] = "§3#2 - §f" + player.getName() + " §7(" + phg.getStarDust() + "✦)";
						top3 = player.getName();
					}
				}
				if(this.main.ScoreUtils.getPlace(player) == 3 && top3 == null) {
					score[5] = "§1#3 - §f" + player.getName() + " §7(" + phg.getStarDust() + "✦)";
					top3 = player.getName();
				}										
			}
			if(this.main.playerhg.containsKey(board.getPlayer().getUniqueId())) {
				score[7] = score[7] + this.main.ScoreUtils.getPlace(board.getPlayer())  + " §7(" + playerhg.getStarDust() + "✦)";
			}			
		    score[9] = score[9] + this.main.GeneralUtils.GetNbGame() + "§f/§b" + this.main.GeneralUtils.GetNbMaxGame();
		    
		    for (int i = 0; i < score.length; i++) {
		      StringBuilder sb = new StringBuilder();
		      sb.append(score[i]);
		      if (sb.length() > 30)
		        sb.delete(29, sb.length() - 1); 
		      score[i] = sb.toString();
		    } 
		    board.updateLines(score);
		    board.updateTitle("§l§3H.GAMES");
	  }
	  
	  public void updateScoreBoardPacMan(FastBoard board) {
		  String[] score = { 
		            "§fTimer : §e",
		            "",
		            "§fVies de Pac-Man : ",
		            "§fNombre de Fantômes : §e",
		            "",
		            "",
		            "",
		            "",
		            "",
		            "",
		            "§fMode de Jeu : §ePac-Man" 
		            };
		  	score[0] = score[0] + this.main.GeneralUtils.conversion(timer);
		    if(this.main.pacman.PacManLives == 3) {
		    	score[2] = score[2] + "§e❤❤❤";
		    } else if (this.main.pacman.PacManLives == 2) {
		    	score[2] = score[2] + "§e❤❤§7❤";
		    } else if (this.main.pacman.PacManLives == 1) {
		    	score[2] = score[2] + "§e❤§7❤❤";
		    } else if (this.main.pacman.PacManLives == 0) {
		    	score[2] = score[2] + "§7❤❤❤";
		    } else {
		    }
		    score[3] = score[3] + this.main.pacman.ghosts.size();
		    
		    for (int i = 0; i < score.length; i++) {
		      StringBuilder sb = new StringBuilder();
		      sb.append(score[i]);
		      if (sb.length() > 30)
		        sb.delete(29, sb.length() - 1); 
		      score[i] = sb.toString();
		    } 
		    board.updateLines(score);
		    board.updateTitle("§l§3H.GAMES");
	  }
	  
	  public void updateScoreBoardPigRun(FastBoard board) {
		  String[] score = { 
		            "§fTimer : §b",
		            "",
		            "§fCheckpoint 1 : ",
		            "§fCheckpoint 2 : ",
		            "§fCheckpoint 3 : ",
		            "§fLigne d'arrivée : ",
		            "",
		            "",
		            "",
		            "",
		            "§fMode de Jeu : §dRun, Piggy!" 
		            };
		  PlayerHG phg = this.main.playerhg.get(board.getPlayer().getUniqueId());
		  UUID PUID = board.getPlayer().getUniqueId();
		  	
		    score[0] = score[0] + this.main.GeneralUtils.conversion((this.getTimer()/100));
		    if(this.main.playerhg.containsKey(board.getPlayer().getUniqueId())) {	    
		    	if (this.main.PigRunUtils.firstCheckPoint.contains(PUID)) {
		    		score[2] = score[2] + "§b" + this.main.GeneralUtils.conversion(phg.getStoredValue1()/100);
		    		score[3] = score[3] + "§9" + this.main.GeneralUtils.conversion((this.getTimer()/100));
		    	} else if (this.main.PigRunUtils.secondCheckPoint.contains(PUID)) {
		    		score[2] = score[2] + "§b" + this.main.GeneralUtils.conversion(phg.getStoredValue1()/100);
		    		score[3] = score[3] + "§b" + this.main.GeneralUtils.conversion(phg.getStoredValue2()/100);
		    		score[4] = score[4] + "§9" + this.main.GeneralUtils.conversion((this.getTimer()/100));
		    	} else if (this.main.PigRunUtils.thirdCheckPoint.contains(PUID)) {
		    		score[2] = score[2] + "§b" + this.main.GeneralUtils.conversion(phg.getStoredValue1()/100);
		    		score[3] = score[3] + "§b" + this.main.GeneralUtils.conversion(phg.getStoredValue2()/100);
		    		score[4] = score[4] + "§b" + this.main.GeneralUtils.conversion(phg.getStoredValue3()/100);
		    		score[5] = score[5] + "§9" + this.main.GeneralUtils.conversion((this.getTimer()/100));
		    	} else if (this.main.PigRunUtils.finished.contains(PUID)) {
		    		score[2] = score[2] + "§b" + this.main.GeneralUtils.conversion(phg.getStoredValue1()/100);
		    		score[3] = score[3] + "§b" + this.main.GeneralUtils.conversion(phg.getStoredValue2()/100);
		    		score[4] = score[4] + "§b" + this.main.GeneralUtils.conversion(phg.getStoredValue3()/100);
		    		score[5] = score[5] + "§b" + this.main.GeneralUtils.conversion(phg.getStoredValue4()/100);
		    	} else {
		    		score[2] = score[2] + "§9" + this.main.GeneralUtils.conversion((this.getTimer()/100));
		    	}
		    } else {
		    	score[2] = "";
		    	score[3] = "";
		    	score[4] = "";
		    	score[5] = "";
		    }
		    
		    for (int i = 0; i < score.length; i++) {
		      StringBuilder sb = new StringBuilder();
		      sb.append(score[i]);
		      if (sb.length() > 30)
		        sb.delete(29, sb.length() - 1); 
		      score[i] = sb.toString();
		    } 
		    board.updateLines(score);
		    board.updateTitle("§l§3H.GAMES");
	  }
	  
	  public void updateScoreBoardCTS(FastBoard board) {
		    String[] score = { 
		            "§fTimer : §e",
		            "",
		            "§e#1 - §f",
		            "§6#2 - §f",
		            "§c#3 - §f",
		            "",
		            "",
		            "§fVotre Équipe : §e",
		            "§fVos moutons : §e",
		            "",
		            "§fMode de Jeu : §6CTS" 
		    };
		    		    
		    List<CTSEnum> teamList = Arrays.asList(CTSEnum.values());
		    for(Integer ind = 0; ind < teamList.size(); ind++) {
		    	CTSEnum team = teamList.get(ind);
		    	if (this.main.CTSUtils.teamPlacement(team) == 1) {
		    		score[2] = score[2] + team.getName();
		    	} else if (this.main.CTSUtils.teamPlacement(team) == 2) {
		    		score[3] = score[3] + team.getName();
		    	} else if (this.main.CTSUtils.teamPlacement(team) == 3) {
		    		score[4] = score[4] + team.getName();
		    	}
		    }
		    
		    if(this.getTimer() >= 0 ) {
		    	score[0] = score[0] + this.main.GeneralUtils.conversion((this.getTimer()));
		    } else {
		    	score[0] = "§eFin de la partie !";
		    }	    
		    if(this.main.playerhg.containsKey(board.getPlayer().getUniqueId())) {
		    	PlayerHG phg = this.main.playerhg.get(board.getPlayer().getUniqueId());
		    	score[7] = score[7] + phg.getTeam().getName();
			    score[8] = score[8] + phg.getTeam().getShNumber();
		    } else {
		    	score[7] = "§7Vous êtes §8Spectateur";
		    	score[8] = "";
		    }
		    
		    for (int i = 0; i < score.length; i++) {
		      StringBuilder sb = new StringBuilder();
		      sb.append(score[i]);
		      if (sb.length() > 30)
		        sb.delete(29, sb.length() - 1); 
		      score[i] = sb.toString();
		    } 
		    board.updateLines(score);
		    board.updateTitle("§l§3H.GAMES");
		  }
	  
	  public void updateScoreBoardRabbitRun(FastBoard board) {
		  String[] score = { 
		            "§fTimer : §a",
		            "",
		            "§a#1 - §2",
		            "§a#2 - §2",
		            "§a#3 - §2",
		            "",
		            "§fVous : §a",
		            "",
		            "§aCliquez sur §2l'émeraude !",
		            "",
		            "§fMode de Jeu : §aRabbit Race" 
		            };
		  PlayerHG playerhg = this.main.playerhg.get(board.getPlayer().getUniqueId());
		  	
		  	top1 = null;
		    top2 = null;
		    top3 = null;
		    
		    score[0] = score[0] + this.main.GeneralUtils.conversion(this.getTimer());
		    List<UUID> players = new ArrayList<>(this.main.playerhg.keySet());
			for (Integer ind = 0; ind < players.size(); ind++) {
				Player player = Bukkit.getPlayer(players.get(ind));
				PlayerHG phg = this.main.playerhg.get(players.get(ind));
				if(this.main.RRUtils.getPlace(player) == 1 && top1 == null) {
					score[2] = score[2] + player.getName() + " §7(" + phg.getStoredValue1() + " clics)";
					top1 = player.getName();
				} else {
					if(this.main.RRUtils.getPlace(player) == 1 && top2 == null) {
						score[3] = score[3] + player.getName() + " §7(" + phg.getStoredValue1() + " clics)";
						top2 = player.getName();
					} else {
						if(this.main.RRUtils.getPlace(player) == 1 && top3 == null) {
							score[4] = score[4] + player.getName() + " §7(" + phg.getStoredValue1() + " clics)";
							top3 = player.getName();
						}
					}
				}
				if(this.main.RRUtils.getPlace(player) == 2 && top2 == null) {
					score[3] = score[3] + player.getName() + " §7(" + phg.getStoredValue1() + " clics)";
					top2 = player.getName();
				} else {
					if(this.main.RRUtils.getPlace(player) == 2 && top3 == null) {
						score[4] = score[4] + player.getName() + " §7(" + phg.getStoredValue1() + " clics)";
						top3 = player.getName();
					}
				}
				if(this.main.RRUtils.getPlace(player) == 3 && top3 == null) {
					score[4] = score[4] + player.getName() + " §7(" + phg.getStoredValue1() + " clics)";
					top3 = player.getName();
				}										
			}
			
			if(this.main.playerhg.containsKey(board.getPlayer().getUniqueId())) {
				score[6] = score[6] + playerhg.getStoredValue1() + " clics";
			} else {
				score[6] = "§7Vous êtes §8Spectateur";
			}
		    
		    for (int i = 0; i < score.length; i++) {
		      StringBuilder sb = new StringBuilder();
		      sb.append(score[i]);
		      if (sb.length() > 30)
		        sb.delete(29, sb.length() - 1); 
		      score[i] = sb.toString();
		    } 
		    board.updateLines(score);
		    board.updateTitle("§l§3H.GAMES");
	  }
	  
	  public void updateScoreBoardDuel(FastBoard board) {
		  String[] score = { 
		            "",
		            "§fRounds Gagnés : §c",
		            "§fRounds Perdus : §c",
		            "",
		            "TIREZ !",
		            "Rounds : §c",
		            "",
		            "§7www.h-party.net" 
		    };
		    
		  Player player = board.getPlayer();
		  PlayerHG phg = this.main.playerhg.get(player.getUniqueId());
		  
		  score[1] = score[1] + phg.getStoredValue1();
		  score[2] = score[2] + phg.getStoredValue2();
		  if(this.main.Duel.getGunTimer() == 0 || this.main.Duel.getGunTimer() == -1) {
			  score[4] = "§4TIREZ !";
		  } else {
			  score[4] = "§fTimer : §c" + this.main.Duel.getGunTimer(); 			  
		  }
		  score[5] = score[5] + this.main.Duel.getRound() + "§f/§c" + this.main.Duel.getMaxRound();
		    	    		    
		    for (int i = 0; i < score.length; i++) {
		      StringBuilder sb = new StringBuilder();
		      sb.append(score[i]);
		      if (sb.length() > 30)
		        sb.delete(29, sb.length() - 1); 
		      score[i] = sb.toString();
		    } 
		    board.updateLines(score);
		    board.updateTitle("§l§3H.GAMES");

	  }
	  
	  public void updateBoard() {
		    for (FastBoard board : this.main.boards.values()) {	
		    	if(this.main.isGame(GamesHG.NULL)) {
		    		if(this.main.isState(StateHG.LOBBY)) {
		    			this.updateScoreBoardLobby(board);
		    		} else if (this.main.isState(StateHG.INTERGAME)) {
		    			this.updateScoreBoardIntergame(board);
		    		}
		    	}
		    	if(this.main.isGame(GamesHG.PACMAN)) {
		    		this.updateScoreBoardPacMan(board);
		    	} else if (this.main.isGame(GamesHG.PIGRUN)) {
		    		this.updateScoreBoardPigRun(board);
		    	} else if (this.main.isGame(GamesHG.CTS)) {
		    		this.updateScoreBoardCTS(board);
		    	} else if (this.main.isGame(GamesHG.RABBIT_RACE)) {
		    		this.updateScoreBoardRabbitRun(board);
		    	} else if (this.main.isGame(GamesHG.DUEL)) {
		    		this.updateScoreBoardDuel(board);
		    	}
		      
		    } 
		  }
	  
	  public int getTimer() {
		  return this.timer;
	  }
	  
	  public void setTimer(int value) {
		  this.timer = value;
	  }
	  
	  public void addTimer() {
		  this.timer++;
	  }
}
