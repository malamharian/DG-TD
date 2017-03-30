package util;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class ImageContainer {

	private static ImageContainer imageContainer = new ImageContainer();
	
	private BufferedImage enemyImg, turretBaseImg, turretHeadImg, starImg;
	
	public BufferedImage getTurretBaseImg() {
		return turretBaseImg;
	}

	public BufferedImage getStarImg() {
		return starImg;
	}

	public void setStarImg(BufferedImage starImg) {
		this.starImg = starImg;
	}

	public void setTurretBaseImg(BufferedImage turretBaseImg) {
		this.turretBaseImg = turretBaseImg;
	}

	public BufferedImage getTurretHeadImg() {
		return turretHeadImg;
	}

	public void setTurretHeadImg(BufferedImage turretHeadImg) {
		this.turretHeadImg = turretHeadImg;
	}

	public static ImageContainer getInstance()
	{
		return imageContainer;
	}
	
	public BufferedImage getEnemyImg()
	{
		return enemyImg;
	}
	
	public ImageContainer()
	{
		try
		{
			starImg = ImageIO.read(getClass().getResourceAsStream("/towers/star.png"));
			enemyImg = ImageIO.read(getClass().getResourceAsStream("/units/enemy.png"));
			turretBaseImg = ImageIO.read(getClass().getResourceAsStream("/towers/TurretBase.png"));
			turretHeadImg = ImageIO.read(getClass().getResourceAsStream("/towers/TurretHead.png"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
