package org.example.follow.me.manager.impl;

import org.example.follow.me.configuration.FollowMeConfiguration;
import org.example.follow.me.manager.*;
import java.util.Map;
import java.util.Set;

import org.example.follow.me.manager.FollowMeAdministration;

import fr.liglab.adele.icasa.service.location.PersonLocationService;
import fr.liglab.adele.icasa.service.preferences.Preferences;

public class FollowMeManagerImpl implements FollowMeAdministration {

	/** Field for configService dependency */
	private FollowMeConfiguration configService;
	/** Preferences services */
	private Preferences preferencesService;
	/** Location services */
	private PersonLocationService personLocationService; // ...
	/** Field for momentOfTheDay dependency */

	@Override
	public void setIlluminancePreference(String person, String goal) {
		// TODO Auto-generated method stub
		preferencesService.setUserPropertyValue(person, USER_PROP_ILLUMINANCE, goal);
	}

	@Override
	public void getIlluminancePreference() {
		IlluminanceGoal illuminanceGoal;
		Set<String> names;
		names = personLocationService.getPersonInZone("livingroom");
		names.addAll(personLocationService.getPersonInZone("kitchen"));
		names.addAll(personLocationService.getPersonInZone("bedroom"));
		names.addAll(personLocationService.getPersonInZone("bathroom"));

		int maxLightsOn = 0;
		double targetedIlluminance = 0;

		for (Object object : names) {
			String element = (String) object;
			String goalCommon = (String) preferencesService.getUserPropertyValue(element, USER_PROP_ILLUMINANCE);
			if (!goalCommon.isEmpty()) {
				System.out.println("Person in flat " + element + "with goal " + goalCommon);
				if (goalCommon.equals(FollowMeAdministration.USER_PROP_ILLUMINANCE_VALUE_SOFT)) {
					illuminanceGoal = IlluminanceGoal.SOFT;
				} else if (goalCommon.equals(FollowMeAdministration.USER_PROP_ILLUMINANCE_VALUE_MEDIUM)) {
					illuminanceGoal = IlluminanceGoal.MEDIUM;
				} else if (goalCommon.equals(FollowMeAdministration.USER_PROP_ILLUMINANCE_VALUE_FULL)) {
					illuminanceGoal = IlluminanceGoal.FULL;
				} else {
					illuminanceGoal = null;
				}
				if (illuminanceGoal != null) {
					if (maxLightsOn < illuminanceGoal.getNumberOfLightsToTurnOn()) {
						maxLightsOn = illuminanceGoal.getNumberOfLightsToTurnOn();
					}
				}
				targetedIlluminance += illuminanceGoal.getIlluminanceValue();
			}
		}
		if (maxLightsOn > 0 && targetedIlluminance > 0) {
			targetedIlluminance = targetedIlluminance / names.size();
			System.out.println("maxLightsOn " + maxLightsOn + "targetedIlluminance " + targetedIlluminance);
			configService.setMaximumNumberOfLightsToTurnOn(maxLightsOn);
			configService.setTargetedIlluminance(targetedIlluminance);
		}
		if (configService.getMaximumNumberOfLightsToTurnOn() == 1) {
			System.out.println("The illuminance goal is SOFT " + configService.getTargetedIlluminance());
		} else if (configService.getMaximumNumberOfLightsToTurnOn() == 2) {
			System.out.println("The illuminance goal is MEDIUM " + configService.getTargetedIlluminance());
		} else if (configService.getMaximumNumberOfLightsToTurnOn() == 3) {
			System.out.println("The illuminance goal is FULL " + configService.getTargetedIlluminance());
		} else {
			System.out.println("The illuminance goal is out of range [1 - 3]");
		}
	}
	

	/** Component Lifecycle Method */
	public void stop() {
		// TODO: Add your implementation code here
	}

	/** Component Lifecycle Method */
	public void start() {
		// TODO: Add your implementation code here
	}

	@Override
	public void setEnergySavingGoal(EnergyGoal energyGoal) {
		// TODO Auto-generated method stub
		configService.setMaximumAllowedEnergyInRoom(energyGoal.getMaximumEnergyInRoom());
	}

	@Override
	public EnergyGoal getEnergyGoal() {
		// TODO Auto-generated method stub
		EnergyGoal energyGoal;
		if (configService.getMaximumAllowedEnergyInRoom() == 100d) {
			energyGoal = EnergyGoal.LOW;
		} else if (configService.getMaximumAllowedEnergyInRoom() == 250d) {
			energyGoal = EnergyGoal.MEDIUM;
		} else if (configService.getMaximumAllowedEnergyInRoom() == 1000d) {
			energyGoal = EnergyGoal.HIGH;
		} else {
			energyGoal = null;
		}
		// System.out.println("getIlluminancePreference");
		return energyGoal;
	}

}