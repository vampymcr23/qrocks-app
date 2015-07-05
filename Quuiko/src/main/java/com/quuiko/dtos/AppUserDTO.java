package com.quuiko.dtos;

import com.quuiko.beans.AppUser;
import com.quuiko.dtos.mobile.JSONDTO;

public class AppUserDTO extends JSONDTO{
	private AppUser appUser;

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}
}
