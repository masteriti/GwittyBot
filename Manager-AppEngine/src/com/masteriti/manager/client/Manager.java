/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.masteriti.manager.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;
import com.masteriti.manager.client.activities.AppActivityMapper;
import com.masteriti.manager.client.places.AppPlaceHistoryMapper;
import com.masteriti.manager.client.places.ListPersonPlace;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Manager implements EntryPoint {

	private Place defaultPlace = new ListPersonPlace("start"); // Starting page/place of our app
	private SimplePanel appWidget = new SimplePanel();  // initial panel for our rootPanel
	
  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
	  
	  // Create ClientFactory using deferred binding to allow us to switch
	  // it out with different implementation in the gwt.xml file
	  ClientFactory clientFactory = GWT.create(ClientFactory.class);
	  EventBus eventBus = clientFactory.getEventBus();
	  PlaceController placeController = clientFactory.getPlaceController();
	  
	  // Start ActivityManager for the main widget with our ActivityMapper
	  ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
	  ActivityManager activityMgr = new ActivityManager(activityMapper, eventBus);
	  activityMgr.setDisplay(appWidget);
	  
	  // Start PlaceHistoryHandler with our PlaceHistoryMapper
	  AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);
	  PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
	  historyHandler.register(placeController, eventBus, defaultPlace);
	  
	  RootPanel.get().add(appWidget);
	  
	  // Goes to the place represented on URL else default place
	  historyHandler.handleCurrentHistory();
  }
}
