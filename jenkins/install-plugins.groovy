import jenkins.model.*
import hudson.PluginManager
import hudson.model.UpdateCenter

def instance = Jenkins.getInstance()

def pluginParameterList = [
  'workflow-job',
  'workflow-cps',
  'cloudbees-folder',
  'credentials',
  'sonar'
]

pluginParameterList.each { plugin ->
  if (!instance.pluginManager.getPlugin(plugin)) {
    def uc = instance.getUpdateCenter()
    def pluginInstance = uc.getPlugin(plugin)
    if (pluginInstance) {
      pluginInstance.deploy().get()
      println("Installed plugin: ${plugin}")
    } else {
      println("Plugin not found: ${plugin}")
    }
  } else {
    println("Plugin already installed: ${plugin}")
  }
}

instance.save()
