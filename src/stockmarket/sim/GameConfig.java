/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stockmarket.sim;


import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

import org.apache.log4j.Logger;

public class GameConfig implements Cloneable{
	public Object clone()
	{
		GameConfig r = new GameConfig(this.confFileName);
		r.num_players = this.num_players;
		r.num_collectors = this.num_collectors;
		r.number_of_rounds = this.number_of_rounds;
		r.max_rounds = this.max_rounds;
		r.playerClass = this.playerClass;
		return r;
	}
	int number_of_rounds;
	int current_round;
	int max_rounds = max_rounds_max;
	ArrayList<Class<Player>> availablePlayers;
	private Class<Player> playerClass;
	public static Random random;
	private Properties props;
	private String confFileName;
	private Logger log = Logger.getLogger(this.getClass());
	int num_players = 5;
	int num_collectors = 1;
	public static int threshold = 50;
	public static int SAFETY_RADIUS = 5;

	public Class<Player> getPlayerClass() {
		return playerClass;
	}

	public void setPlayerClass(Class<Player> playerClass) {
		this.playerClass = playerClass;
	}


	public ArrayList<Player> getPlayers() {
		return players;
	}

	ArrayList<Player> players;

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public static final int max_rounds_max = 5000;

	public int getNumPlayers() {
		return num_players;
	}

	public void setNumPlayers(int num_players) {
		this.num_players = num_players;
	}

	public void setMaxRounds(int v) {
		this.max_rounds = v;
	}

	public int getMaxRounds() {
		return max_rounds;
	}

	public GameConfig(String filename) {
		confFileName = filename;
		props = new Properties();
		availablePlayers = new ArrayList<Class<Player>>();
		load();
	}

	/**
	 * Read in configuration file.
	 * 
	 * @param file
	 */
	public void load() {
		try {
			FileInputStream in = new FileInputStream(confFileName);
			props.loadFromXML(in);
		} catch (IOException e) {
			System.err.println("Error reading configuration file:"
					+ e.getMessage());
			e.printStackTrace();
			System.exit(-1);
		}
		extractProperties();
	}

	/**
	 * Get the game configuration parameters out of the Property object.
	 * 
	 */
	private void extractProperties() {
		String s;

		// READ IN CLASSES
		s = props.getProperty("stockmarket.classes");
		if (s != null) {
			String[] names = s.split(" ");
			for (int i = 0; i < names.length; i++) {
				try {
					availablePlayers.add((Class<Player>) Class
							.forName(names[i]));
				} catch (ClassNotFoundException e) {
					log.error("[Configuration] Class not found: " + names[i]);
				}
			}
		}
		
		File sourceFolder = new File("bin/stockmarket/");
		for(File f : sourceFolder.listFiles())
		{
			if(f.getName().length() == 2 && f.getName().substring(0,1).equals("g"))
			{
				for(File c : f.listFiles())
				{
					if(c.getName().endsWith(".class") ){
						String className = c.toString().replaceAll("/", ".").replace("bin.","");
						className = className.substring(0, className.length() - 6);
						 Class theClass = null;
				          try{
				            theClass = Class.forName(className, false,this.getClass().getClassLoader());
				            if(theClass.getSuperclass() != null && theClass.getSuperclass().toString().equals("class stockmarket.sim.Player"))
				            {
				            	if(!availablePlayers.contains((Class<Player>) theClass))
				            		availablePlayers.add((Class<Player>) theClass);
				            }
				          }catch(NoClassDefFoundError e){
				            continue;
				          } catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							continue;
						}

					}
					else if(c.isDirectory())
					{
						for(File ca : c.listFiles())
						{
							if(ca.getName().endsWith(".class") ){
								String className = ca.toString().replace(c.toString(),"").replaceAll("/", ".");
								className = className.substring(0, className.length() - 6);
								 Class theClass = null;
						          try{
						            theClass = Class.forName(className, false,this.getClass().getClassLoader());
						            if(theClass.getSuperclass() != null && theClass.getSuperclass().toString().equals("class stockmarket.sim.Player"))
						            {
						            	if(!availablePlayers.contains((Class<Player>) theClass))
						            		availablePlayers.add((Class<Player>) theClass);
						            }
						          }catch(NoClassDefFoundError e){
						            continue;
						          } catch (ClassNotFoundException e) {
									// TODO Auto-generated catch block
									continue;
								}

							}
							else if(c.isDirectory())
							{
								
							}
						}
					}
				}
			}
		}
		if (availablePlayers.size() == 0)
			log.fatal("No player classes loaded!!!");
		if(props.getProperty("stockmarket.seed") != null)
		{
			long seed = Long.valueOf(props.getProperty("stockmarket.seed"));
			random = new Random(seed);
		}
		else
			random = new Random();
	}



//	public ComboBoxModel getPlayerListModel() {
//		DefaultComboBoxModel m = new DefaultComboBoxModel();
//		for (Class c : availablePlayers) {
//			m.addElement(c);
//		}
//		return m;
//	}


}
