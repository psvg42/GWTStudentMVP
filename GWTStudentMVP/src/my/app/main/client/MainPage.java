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
package my.app.main.client;

import my.app.main.client.mvp.AppPlaceHistoryMapper;
import my.app.main.client.mvp.ContentActivityMaper;
import my.app.main.client.mvp.LeftMenuActivityMapper;
import my.app.main.client.place.MainPagePlace;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class MainPage implements EntryPoint {

	private SimplePanel content = new SimplePanel();
	private SimplePanel left = new SimplePanel();

	private Place defaultPlace = new MainPagePlace("start");

	@SuppressWarnings("deprecation")
	public void onModuleLoad() {
		// Create ClientFactory using deferred binding so we can replace with
		// different impls in gwt.xml
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		EventBus eventBus = clientFactory.getEventBus();
		PlaceController placeController = clientFactory.getPlaceController();

		// Start ActivityManager for the main widget with our ActivityMapper
		ActivityMapper activityMapperContent = new ContentActivityMaper(clientFactory);
		ActivityManager activityManagerContent = new ActivityManager(activityMapperContent, eventBus);
		activityManagerContent.setDisplay(content);

		// Start ActivityManager for the main widget with our ActivityMapper
		ActivityMapper activityMapperLeftMenu = new LeftMenuActivityMapper(clientFactory);
		ActivityManager activityManagerLeftMenu = new ActivityManager(activityMapperLeftMenu, eventBus);
		activityManagerLeftMenu.setDisplay(left);
		
		// Start PlaceHistoryHandler with our PlaceHistoryMapper
		AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
		historyHandler.register(placeController, eventBus, defaultPlace);
		RootPanel.get("content").add(content);
		RootPanel.get("leftnav").add(left);

		// Goes to place represented on URL or default place
		historyHandler.handleCurrentHistory();
	}
}
