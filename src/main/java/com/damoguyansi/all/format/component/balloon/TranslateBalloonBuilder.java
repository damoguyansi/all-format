package com.damoguyansi.all.format.component.balloon;

import com.damoguyansi.all.format.util.ColorUtil;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.BalloonBuilder;
import com.intellij.openapi.ui.popup.JBPopupListener;
import com.intellij.openapi.ui.popup.LightweightWindowEvent;
import com.intellij.openapi.util.Disposer;
import com.intellij.ui.BalloonImpl;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class TranslateBalloonBuilder implements BalloonBuilder {
    private static final Map<Disposable, List<Balloon>> DEFAULT_STORAGE = new WeakHashMap<>();
    @Nullable
    private final Map<Disposable, List<Balloon>> myStorage;
    @Nullable
    private Disposable myAnchor;

    private final JComponent myContent;

    private Color myBorder = UIManager.getColor("ToolTip.border");
    @Nullable
    private Insets myBorderInsets = null;
    private Color myFill = MessageType.INFO.getPopupBackground();
    private boolean myHideOnMouseOutside = true;
    private boolean myHideOnKeyOutside = true;
    private long myFadeoutTime = -1;
    private boolean myShowCallout = true;
    private boolean myCloseButtonEnabled = false;
    private boolean myHideOnFrameResize = true;
    private boolean myHideOnLinkClick = false;

    private ActionListener myClickHandler;
    private boolean myCloseOnClick;
    private int myAnimationCycle = 200;

    private int myCalloutShift;
    private int myPositionChangeXShift;
    private int myPositionChangeYShift;
    private boolean myHideOnAction = true;
    private boolean myHideOnCloseClick = true;
    private boolean myDialogMode;
    private String myTitle;
    private Insets myContentInsets = JBUI.insets(2);
    private boolean myShadow = ColorUtil.isDarcula();
    private boolean mySmallVariant = false;

    private Balloon.Layer myLayer;
    private boolean myBlockClicks = false;
    private boolean myRequestFocus = false;

    private Dimension myPointerSize;
    private int myCornerToPointerDistance = -1;

    public TranslateBalloonBuilder(@NotNull final JComponent content) {
        this(DEFAULT_STORAGE, content);
    }

    public TranslateBalloonBuilder(@Nullable Map<Disposable, List<Balloon>> storage, @NotNull final JComponent content) {
        myStorage = storage;
        myContent = content;

        Object value = myContent.getClientProperty(BalloonImpl.FORCED_NO_SHADOW);
        if (Boolean.TRUE.equals(value)) {
            myShadow = false;
        }
    }

    @NotNull
    @Override
    public BalloonBuilder setHideOnAction(boolean hideOnAction) {
        myHideOnAction = hideOnAction;
        return this;
    }

    @NotNull
    @Override
    public BalloonBuilder setDialogMode(boolean dialogMode) {
        myDialogMode = dialogMode;
        return this;
    }

    @NotNull
    @Override
    public BalloonBuilder setBorderColor(@NotNull final Color color) {
        myBorder = color;
        return this;
    }

    @NotNull
    @Override
    public BalloonBuilder setBorderInsets(@Nullable Insets insets) {
        myBorderInsets = insets;
        return this;
    }

    @NotNull
    @Override
    public BalloonBuilder setFillColor(@NotNull final Color color) {
        myFill = color;
        return this;
    }

    @NotNull
    @Override
    public BalloonBuilder setHideOnClickOutside(final boolean hide) {
        myHideOnMouseOutside = hide;
        return this;
    }

    @NotNull
    @Override
    public BalloonBuilder setHideOnKeyOutside(final boolean hide) {
        myHideOnKeyOutside = hide;
        return this;
    }

    @NotNull
    @Override
    public BalloonBuilder setShowCallout(final boolean show) {
        myShowCallout = show;
        return this;
    }

    @NotNull
    @Override
    public BalloonBuilder setFadeoutTime(long fadeoutTime) {
        myFadeoutTime = fadeoutTime;
        return this;
    }

    @NotNull
    @Override
    public BalloonBuilder setBlockClicksThroughBalloon(boolean block) {
        myBlockClicks = block;
        return this;
    }

    @NotNull
    @Override
    public BalloonBuilder setRequestFocus(boolean requestFocus) {
        myRequestFocus = requestFocus;
        return this;
    }

    @Override
    public BalloonBuilder setHideOnCloseClick(boolean hideOnCloseClick) {
        myHideOnCloseClick = hideOnCloseClick;
        return this;
    }

    @NotNull
    @Override
    public BalloonBuilder setAnimationCycle(int time) {
        myAnimationCycle = time;
        return this;
    }

    @NotNull
    @Override
    public BalloonBuilder setHideOnFrameResize(boolean hide) {
        myHideOnFrameResize = hide;
        return this;
    }

    @NotNull
    @Override
    public BalloonBuilder setHideOnLinkClick(boolean hide) {
        myHideOnLinkClick = hide;
        return this;
    }

    @NotNull
    @Override
    public BalloonBuilder setPositionChangeXShift(int positionChangeXShift) {
        myPositionChangeXShift = positionChangeXShift;
        return this;
    }

    @NotNull
    @Override
    public BalloonBuilder setPositionChangeYShift(int positionChangeYShift) {
        myPositionChangeYShift = positionChangeYShift;
        return this;
    }

    @NotNull
    @Override
    public BalloonBuilder setCloseButtonEnabled(boolean enabled) {
        myCloseButtonEnabled = enabled;
        return this;
    }

    @NotNull
    @Override
    public BalloonBuilder setClickHandler(ActionListener listener, boolean closeOnClick) {
        myClickHandler = listener;
        myCloseOnClick = closeOnClick;
        return this;
    }

    @NotNull
    @Override
    public BalloonBuilder setCalloutShift(int length) {
        myCalloutShift = length;
        return this;
    }

    @NotNull
    @Override
    public BalloonBuilder setTitle(@Nullable String title) {
        myTitle = title;
        return this;
    }

    @NotNull
    @Override
    public BalloonBuilder setContentInsets(Insets insets) {
        myContentInsets = insets;
        return this;
    }

    @NotNull
    @Override
    public BalloonBuilder setShadow(boolean shadow) {
        myShadow = shadow;
        return this;
    }

    @NotNull
    @Override
    public BalloonBuilder setSmallVariant(boolean smallVariant) {
        mySmallVariant = smallVariant;
        return this;
    }

    @NotNull
    @Override
    public BalloonBuilder setLayer(Balloon.Layer layer) {
        myLayer = layer;
        return this;
    }

    @NotNull
    @Override
    public BalloonBuilder setDisposable(@NotNull Disposable anchor) {
        myAnchor = anchor;
        return this;
    }

    @NotNull
    @Override
    public BalloonBuilder setPointerSize(Dimension size) {
        myPointerSize = size;
        return this;
    }

    @NotNull
    @Override
    public BalloonBuilder setCornerToPointerDistance(int distance) {
        myCornerToPointerDistance = distance;
        return this;
    }

    @NotNull
    @Override
    public Balloon createBalloon() {
        final BalloonImpl result = new BalloonImpl(
                myContent, myBorder, myBorderInsets, myFill, myHideOnMouseOutside, myHideOnKeyOutside, myHideOnAction, myHideOnCloseClick,
                myShowCallout, myCloseButtonEnabled, myFadeoutTime, myHideOnFrameResize, myHideOnLinkClick, myClickHandler, myCloseOnClick,
                myAnimationCycle, myCalloutShift, myPositionChangeXShift, myPositionChangeYShift, myDialogMode, myTitle, myContentInsets, myShadow,
                mySmallVariant, myBlockClicks, myLayer, myRequestFocus, myPointerSize, myCornerToPointerDistance);

        if (myStorage != null && myAnchor != null) {
            List<Balloon> balloons = myStorage.get(myAnchor);
            if (balloons == null) {
                myStorage.put(myAnchor, balloons = new ArrayList<>());
                Disposer.register(myAnchor, () -> {
                    List<Balloon> toDispose = myStorage.remove(myAnchor);
                    if (toDispose != null) {
                        for (Balloon balloon : toDispose) {
                            if (!balloon.isDisposed()) {
                                Disposer.dispose(balloon);
                            }
                        }
                    }
                });
            }
            balloons.add(result);
            result.addListener(new JBPopupListener() {
                @Override
                public void onClosed(@NotNull LightweightWindowEvent event) {
                    if (!result.isDisposed()) {
                        Disposer.dispose(result);
                    }
                }
            });
        }

        return result;
    }
}
