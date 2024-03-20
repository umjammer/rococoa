/*
 * Copyright 2007, 2008, 2009 Duncan McGregor
 *
 * This file is part of Rococoa, a library to allow Java to talk to Cocoa.
 *
 * Rococoa is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Rococoa is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Rococoa.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.rococoa.cocoa;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFrame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.rococoa.ID;
import org.rococoa.Rococoa;
import org.rococoa.ObjCObject;
import org.rococoa.cocoa.appkit.NSOpenPanel;
import org.rococoa.cocoa.foundation.NSURL;
import org.rococoa.test.RococoaTestCase;
import vavi.util.Debug;


public class NSOpenPanelTest extends RococoaTestCase {

    // Requires user to select a text file somewhere downtree from ~
    @Test
    @EnabledIfSystemProperty(named = "vavi.test", matches = "ide")
    public void testShow() {
        new JFrame().setVisible(true); // otherwise no panel
        NSOpenPanel panel = NSOpenPanel.CLASS.openPanel();
        
        // Keep this reference!
        ObjCObject ocProxy = Rococoa.proxy(new Object() {
            @SuppressWarnings("unused")
            public boolean panel_shouldEnableURL(ID panel, NSURL url) {
Debug.println("url: " + url);
                char initialChar = new File(url.path()).getName().toLowerCase().charAt(0);
                return initialChar % 2 == 0;
            }
        });
Debug.println("proxy: " + ocProxy);

        panel.setTitle("Open Sesame!");
        panel.setDelegate(ocProxy.id());
        int button = panel.runModalForTypes(null);
//              or, eg        
//                NSArray.CLASS.arrayWithObjects(
//                    NSString.stringWithString("txt"), null));
        String filename = panel.filename();
Debug.println("filename: " + filename);
        if (button == NSOpenPanel.NSOKButton) {
            assertTrue(Files.exists(Paths.get(filename)));
        } else {
            assertEquals(NSOpenPanel.NSCancelButton, button);
            assertNull(filename);
        }
    }
}
