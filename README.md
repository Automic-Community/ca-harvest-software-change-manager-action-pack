## Getting Started:


###### Description

CA Harvest Software Change Manager is a release management solution that provides powerful, process-driven capabilities for managing development teams across your enterprise.

By integrating CA Harvest Software Change Manager with CA Continuous Delivery Automation (formerly known as CA Automic Release Automation), you will do a "shift left" and will benefit from a CI/CD pipeline starting from the source code management. 
		
###### Actions

1. Create Package
2. Get Packagr
3. Promote Package
4. Demote Package
5. Check-Out Local
6. Check-Out By Agent
7. Check-In Local
8. Check-In By Agent
9. Get Version
10. Take Snapshot

		
###### Compatibility:

1. Oracle JRE 1.7 or later
2. CA Harvest 12.6
4. Maven

###### Prerequisite:

1. Automation Engine should be installed.
2. Automic Package Manager should be installed.
3. ITPA Shared action pack should be installed.
4. Harvest library 12.6

###### Steps to install action pack source code:

1. Clone the code to your machine.
2. Go to the directory inside the package directory containg the pom.xml file.(source/tools/)
3. **Keep the be jars in the given directory tree structure. Here harvest_lib is parallel to src directory**.
	1. **harvest_lib > harvest > cmsdk > 12.6 > cmsdk-12.6.jar**
	2. **harvest_lib > haragent > haragent > 12.6 > haragent-12.6.jar**
	3. **harvest_lib > haragent > hcomm > 12.6 > hcomm-12.6.jar**
	4. **harvest_lib > haragent > hcontainer> 12.6 > hcontainer-12.6.jar**
	5. **harvest_lib > haragent > hutils > 12.6 > hutils-12.6.jar**
3. Open the terminal and run the maven package command. Example: **mvn clean package -DskipTests**
4. Run the command apm upload in the directory which contains package.yml (source/):
   Ex. **apm upload -force -u <Name>/<Department> -c <Client-id> -H <Host> -pw <Password> -S AUTOMIC -y -ia -ru**


###### Package/Action Documentation

Please refer to the link for [package documentation](source/ae/DOCUMENTATION/PCK.AUTOMIC_DOCKER.PUB.DOC.xml)

###### Third party licenses:

The third-party library and license document reference.[Third party licenses](source/ae/DOCUMENTATION/PCK.AUTOMIC_DOCKER.PUB.LICENSES.xml)

###### Useful References

1. [About Packs and Plug-ins](https://docs.automic.com/documentation/webhelp/english/AA/12.3/DOCU/12.3/Automic%20Automation%20Guides/help.htm#PluginManager/PM_AboutPacksandPlugins.htm?Highlight=Action%20packs)
2. [Working with Packs and Plug-ins](https://docs.automic.com/documentation/webhelp/english/AA/12.3/DOCU/12.3/Automic%20Automation%20Guides/help.htm#PluginManager/PM_WorkingWith.htm#link10)
3. [Actions and Action Packs](https://docs.automic.com/documentation/webhelp/english/AA/12.3/DOCU/12.3/Automic%20Automation%20Guides/help.htm#_Common/ReleaseHighlights/RH_Plugin_PackageManager.htm?Highlight=Action%20packs)
4. [PACKS Compatibility Mode](https://docs.automic.com/documentation/webhelp/english/AA/12.3/DOCU/12.3/Automic%20Automation%20Guides/help.htm#AWA/Variables/UC_CLIENT_SETTINGS/UC_CLIENT_PACKS_COMPATIBILITY_MODE.htm?Highlight=Action%20packs)
5. [Working with actions](https://docs.automic.com/documentation/webhelp/english/AA/12.3/DOCU/12.3/Automic%20Automation%20Guides/help.htm#ActionBuilder/AB_WorkingWith.htm#link4)
6. [Installing and Configuring the Action Builder](https://docs.automic.com/documentation/webhelp/english/AA/12.3/DOCU/12.3/Automic%20Automation%20Guides/help.htm#ActionBuilder/install_configure_plugins_AB.htm?Highlight=Action%20packs)

###### Distribution: 

In the distribution process, we can download the existing or updated action package from the Automation Engine by using the apm build command.
Example: **apm build -y -H AE_HOST -c 106 -u TEST/TEST -pw password -d /directory/ -o zip -v action_pack_name**
			
			
###### Copyright and License: 

Broadcom does not support, maintain or warrant Solutions, Templates, Actions and any other content published on the Community and is subject to Broadcom Community [Terms and Conditions](https://community.broadcom.com/termsandconditions)