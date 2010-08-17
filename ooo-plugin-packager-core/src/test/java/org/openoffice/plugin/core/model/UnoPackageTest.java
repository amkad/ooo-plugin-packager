/*************************************************************************
 * UnoPackageTest.java
 *
 * The Contents of this file are made available subject to the terms of
 * either of the GNU Lesser General Public License Version 2.1
 * 
 * GNU Lesser General Public License Version 2.1
 * =============================================
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1, as published by the Free Software Foundation.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 * MA 02111-1307 USA
 *
 * Contributor(s): oliver.boehm@agentes.de
 ************************************************************************/

package org.openoffice.plugin.core.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.*;

/**
 * This is the first JUnit test class for the ooo-plugin-package-core project.
 * 
 * @author oliver (oliver.boehm@agentes.de)
 * @since 0.0.1 (16.08.2010)
 */
public final class UnoPackageTest {
	
	private static final Log log = LogFactory.getLog(UnoPackage.class);
	private static File tmpDir;
	private File tmpFile;
	private UnoPackage pkg;
	
	/**
	 * Here we create a tmpdir with some files for testing.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@BeforeClass
	public static void setUpTmpDir() throws IOException {
		tmpDir = new File(System.getProperty("java.io.tmpdir", "/tmp"), "oxttest" + System.currentTimeMillis());
		assertTrue("can't create " + tmpDir, tmpDir.mkdir());
		assertTrue(tmpDir + " is not a directory", tmpDir.isDirectory());
		FileUtils.writeStringToFile(new File(tmpDir, "README"), "README for testing");
		FileUtils.writeStringToFile(new File(tmpDir, "hello.properties"), "hello=world");
		log.info(tmpDir + " with 2 files created");
	}

	/**
	 * Creates a tmp file for testing.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Before
	public void setUp() throws IOException {
		tmpFile = File.createTempFile("test", ".oxt");
		log.info("using " + tmpFile + " for testing...");
		pkg = new UnoPackage(tmpFile);
	}
	
	/**
	 * Here we delete the tmp file after testing.
	 */
	@After
	public void tearDown() {
		tmpFile.delete();
		log.info(tmpFile + " is deleted.");
	}
	
	/**
	 * Here we delete the directory created for testing.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@AfterClass
	public static void tearDownTmpDir() throws IOException {
		FileUtils.deleteDirectory(tmpDir);
		log.info(tmpDir + " is deleted.");
	}

	/**
	 * Test method for {@link UnoPackage#UnoPackage(java.io.File)}.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testUnoPackage() throws IOException {
		pkg.close();
		assertTrue(tmpFile.isFile());
	}
	
	/**
	 * Here we create just two testfiles and check if this will be part of the
	 * created oxt file.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testAddContent() throws IOException {
		pkg.addContent(tmpDir);
		List<File> files = pkg.getContainedFiles();
		assertEquals(2, files.size());
		pkg.close();
	}

}
