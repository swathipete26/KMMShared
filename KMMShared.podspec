Pod::Spec.new do |spec|
    spec.name                     = 'KMMShared'
    spec.version                  = '1.0.0'
    spec.homepage                 = 'https://github.com/swathipete26/KMMShared'
    spec.source                   = { :git => "Not Published", :tag => "Cocoapods/#{spec.name}/#{spec.version}" }
    spec.authors                  = { 'swathipete26' => 'swathi.p@atherenergy.com' }
    spec.license                  = { :type => "MIT", :file => "LICENSE" }
    spec.summary                  = 'KMM Shared Library'

    spec.vendored_frameworks      = "build/cocoapods/framework/MyFramework.framework"
    spec.libraries                = "c++"
    spec.module_name              = "#{spec.name}_umbrella"

    spec.ios.deployment_target = '13.0'

                

    spec.pod_target_xcconfig = {
        'KOTLIN_PROJECT_PATH' => ':KMMShared',
        'PRODUCT_MODULE_NAME' => 'KMMShared',
    }

    spec.script_phases = [
        {
            :name => 'Build KMMShared',
            :execution_position => :before_compile,
            :shell_path => '/bin/sh',
            :script => <<-SCRIPT
                if [ "YES" = "$COCOAPODS_SKIP_KOTLIN_BUILD" ]; then
                  echo "Skipping Gradle build task invocation due to COCOAPODS_SKIP_KOTLIN_BUILD environment variable set to \"YES\""
                  exit 0
                fi
                set -ev
                REPO_ROOT="$PODS_TARGET_SRCROOT"
                "$REPO_ROOT/../gradlew" -p "$REPO_ROOT" $KOTLIN_PROJECT_PATH:syncFramework \
                    -Pkotlin.native.cocoapods.platform=$PLATFORM_NAME \
                    -Pkotlin.native.cocoapods.archs="$ARCHS" \
                    -Pkotlin.native.cocoapods.configuration=$CONFIGURATION
            SCRIPT
        }
    ]
end
