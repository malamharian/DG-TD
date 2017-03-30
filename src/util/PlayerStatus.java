package util;


import gamestate.PlayState;
import main.GamePanel;
import object.GuiText;

public class PlayerStatus {

	private int score, life, money, level;
	private GuiText txtMoney, txtScore, txtLife, txtLevel;
	private PlayState playState;
	
	public int getScore() {
		return score;
	}
	
	public void setLevel(int level)
	{
		this.level = level;
		txtLevel.setText("Level: " + level);
	}
	
	public int getLevel()
	{
		return level;
	}

	public void setScore(int score) {
		this.score = score;
		txtScore.setText("Score: " + score);
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
		txtLife.setText("Life: " + life);
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
		txtMoney.setText("Money: " + money);
	}

	public PlayerStatus(PlayState playState)
	{
		score = life = money = 0;
		level = 1;
		txtMoney = new GuiText("", 10, 20, 12);
		txtScore = new GuiText("", GamePanel.WIDTH-80, 20, 12);
		txtLife = new GuiText("", 10, 40, 12);
		txtLevel = new GuiText("Level: " + level, GamePanel.WIDTH-80, 40, 12);		
		this.playState = playState;
	}
	
	public void init()
	{
		setScore(score);
		setLife(life);
		setMoney(money);
		playState.addGuiText(txtScore);
		playState.addGuiText(txtLife);
		playState.addGuiText(txtMoney);
		playState.addGuiText(txtLevel);
	}
}
