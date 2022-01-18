package com.one.bee.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.res.AssetManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.one.bee.model.Destination;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.ActivityNavigator;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavGraphNavigator;
import androidx.navigation.NavigatorProvider;
import androidx.navigation.fragment.DialogFragmentNavigator;
import androidx.navigation.fragment.FragmentNavigator;

/**
 * @author diaokaibin@gmail.com on 2022/1/18.
 */
public class NavUtil {
    public static HashMap<String, Destination> getDestinations(Context context) {
        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open("destination.json");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = null;

            StringBuilder sb = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            inputStream.close();
            bufferedReader.close();

            HashMap<String, Destination> destinationHashMap = JSON.parseObject(sb.toString(), new TypeReference<HashMap<String, Destination>>() {
            }.getType());

            return destinationHashMap;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void builderNavGraph(FragmentActivity activity, NavController controller, int containerId) {
        HashMap<String, Destination> destinations = getDestinations(activity);
        Iterator<Destination> iterator = destinations.values().iterator();
        NavigatorProvider provider = controller.getNavigatorProvider();
        NavGraphNavigator graphNavigator = provider.getNavigator(NavGraphNavigator.class);
        NavGraph navGraph = new NavGraph(graphNavigator);

        while (iterator.hasNext()) {
            Destination destination = iterator.next();
            if (destination.destType.equals("activity")) {
                ActivityNavigator navigator = provider.getNavigator(ActivityNavigator.class);
                ActivityNavigator.Destination node = navigator.createDestination();
                node.setId(destination.id);
                node.setComponentName(new ComponentName(activity.getPackageName(), destination.clazzName));
                navGraph.addDestination(node);


            } else if (destination.destType.equals("fragment")) {

                FragmentNavigator navigator = provider.getNavigator(FragmentNavigator.class);
                FragmentNavigator.Destination node = navigator.createDestination();
                node.setId(destination.id);
                node.setClassName(destination.clazzName);
                navGraph.addDestination(node);

            } else if (destination.destType.equals("dialog")) {

                DialogFragmentNavigator navigator = provider.getNavigator(DialogFragmentNavigator.class);
                DialogFragmentNavigator.Destination node = navigator.createDestination();
                node.setId(destination.id);
                node.setClassName(destination.clazzName);
                navGraph.addDestination(node);
            }

            if (destination.asStarter) {
                navGraph.setStartDestination(destination.id);
            }
        }
        controller.setGraph(navGraph);

    }
} 