/**
 * SplashScreen
 * 启动屏
 * from：http://www.devio.org
 * Author:CrazyCodeBoy
 * GitHub:https://github.com/crazycodeboy
 * Email:crazycodeboy@gmail.com
 */
#import <React/RCTBridgeModule.h>
#import <React/RCTRootView.h>

@interface SplashScreen : NSObject<RCTBridgeModule>
+ (void)show:(RCTRootView *)v;
+ (void)hide;
@end
