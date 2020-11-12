/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2020 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package es.udc.fi.dc.fd.controller.ad;

import static com.google.common.base.Preconditions.checkNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.udc.fi.dc.fd.controller.entity.ExampleEntityViewConstants;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.service.ad.AdEntityService;
import es.udc.fi.dc.fd.service.user.UserService;

/**
 * Controller for the example entities listing view.
 * <p>
 * This serves as an adapter between the UI and the services layer.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 */
@Controller
@RequestMapping("/advertisement")
public class AdEntityListViewController {

	/**
	 * Example entity service.
	 */
	private final AdEntityService adEntityService;

	private final UserService userService;

	/**
	 * Constructs a controller with the specified dependencies.
	 * 
	 * @param service example entity service
	 */
	@Autowired
	public AdEntityListViewController(final AdEntityService service, final UserService service2) {
		super();

		adEntityService = checkNotNull(service, "Received a null pointer as service");
		userService = checkNotNull(service2, "Received a null pointer as service");

	}

	/**
	 * Shows the entities listing view.
	 * <p>
	 * Actually it just returns the name of the view. Spring will take care of the
	 * rest.
	 * <p>
	 * Before returning the name the model should be loaded with all the data
	 * required by the view.
	 * 
	 * @param model model map
	 * @return the name for the entities listing view
	 */
	@GetMapping(path = "/list")
	public String showAdEntityList(final ModelMap model) {
		// Loads required data into the model
		loadViewModel(model);

		return AdEntityViewConstants.VIEW_ENTITY_LIST;
	}
	
	@GetMapping(path = "/followed")
	public String showFollowedAdEntityList(final ModelMap model) {
		// Loads required data into the model
		loadViewModel(model);

		return AdEntityViewConstants.VIEW_FOLLOWED_ENTITY_LIST;
	}

	/**
	 * Loads the model data required for the entities listing view.
	 * <p>
	 * As the view will list all the entities, it requires these entities as one of
	 * the parameters.
	 * 
	 * @param model model map
	 */
	private final void loadViewModel(final ModelMap model) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		final UserEntity userEntity = userService.findByUsername(username);
		model.put(ExampleEntityViewConstants.PARAM_ENTITIES, adEntityService.getAllEntities());
		model.put("user", userEntity);
		model.put("follows", userEntity.getFollowed());
	}

}
