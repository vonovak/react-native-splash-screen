/**
 * SplashScreen
 * 启动屏
 * from：http://www.devio.org
 * Author:CrazyCodeBoy
 * GitHub:https://github.com/crazycodeboy
 * Email:crazycodeboy@gmail.com
 */

#import "SplashScreen.h"
#import <React/RCTBridge.h>

static bool addedJsLoadErrorObserver = false;
static UIView* splashViewRef;
static RCTRootView* rootViewRef;

@implementation SplashScreen
- (dispatch_queue_t)methodQueue{
    return dispatch_get_main_queue();
}

RCT_EXPORT_MODULE()

+ (void)show:(RCTRootView *)rootview {
    if (!addedJsLoadErrorObserver) {
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(jsLoadError:) name:RCTJavaScriptDidFailToLoadNotification object:nil];
        addedJsLoadErrorObserver = true;
    }
    
    UIView *splashView = [[[NSBundle mainBundle] loadNibNamed:@"LaunchScreen" owner:self options:nil] objectAtIndex:0];
    splashView.frame = rootview.bounds;
    splashViewRef = splashView;
    rootViewRef = rootview;
    [rootview addSubview:splashView];
}

+ (void)hide {
    float duration = 0.25;
    dispatch_time_t delay = 0;
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(delay * NSEC_PER_SEC)),
                   dispatch_get_main_queue(), ^{
                       [UIView transitionWithView:rootViewRef
                                         duration:duration
                                          options:UIViewAnimationOptionTransitionCrossDissolve
                                       animations:^{
                                           splashViewRef.hidden = YES;
                                       } completion:^(__unused BOOL finished) {
                                           [splashViewRef removeFromSuperview];
                                       }];
                   });
}

+ (void) jsLoadError:(NSNotification*)notification
{
    // If there was an error loading javascript, hide the splash screen so it can be shown.  Otherwise the splash screen will remain forever, which is a hassle to debug.
    [SplashScreen hide];
}

RCT_EXPORT_METHOD(hide) {
    [SplashScreen hide];
}

@end
