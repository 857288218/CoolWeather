package com.example.rjq.coolweather.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class FormatterUtil {

    public static final String TAG = NumberFormat.class.getSimpleName();

    private static NumberFormat coinFormatter;
    private static NumberFormat kbFormatter;
    private static NumberFormat coinInputFormatter;
    private static NumberFormat currencyInputFormatter;
    private static NumberFormat percentFormatter;
    private static NumberFormat percentNoSignFormatter;
    private static NumberFormat percentFormatterDownOrUp;
    private static NumberFormat percentFormatterUp;
    private static NumberFormat percentNoSignFormatterTwo;
    private static NumberFormat percentNoFractionFormatter;
    private static NumberFormat percentNoSignTwoDecimalFormatter;
    private static SimpleDateFormat dateFormat;
    private static SimpleDateFormat minuteDateFormat;
    private static SimpleDateFormat monthDateFormat;
    private static SimpleDateFormat monthDateFormatVice;
    private static SimpleDateFormat YMDateFormat;
    private static SimpleDateFormat YMDDateFormatVice;
    private static SimpleDateFormat YMDDateFormatSeparator;
    private static SimpleDateFormat YMDDateFormat;
    private static SimpleDateFormat MDDateFormat;
    private static SimpleDateFormat YMDDateFormatChina;//2019年03月19日
    private static SimpleDateFormat HMateFormat;// 14：15

    public static final double EPSION = 1e-13;
    private static SimpleDateFormat dayDateFormat;
    private static final double BILLION = 1.0e8;
    private static final double THOUSAND = 1.0e3;
    public static Calendar calendarDay;

    // 交易专用的Coin的Formatter
    public static NumberFormat getMarketCoinFormatter() {
        if (coinFormatter == null) {
            synchronized (FormatterUtil.class) {
                coinFormatter = NumberFormat.getNumberInstance(Locale.CHINA);
                coinFormatter.setMaximumFractionDigits(6);
            }
        }
        return coinFormatter;
    }

    // 最多8位小数的CoinFormatter
    public static NumberFormat getCoinFormatter() {
        if (coinInputFormatter == null) {
            synchronized (FormatterUtil.class) {
                coinInputFormatter = NumberFormat.getNumberInstance(Locale.CHINA);
                coinInputFormatter.setMaximumFractionDigits(8);
                //取消千分符
                coinInputFormatter.setGroupingUsed(false);
            }
        }
        return coinInputFormatter;
    }

    // 最多fractionCount位小数的CoinFormatter
    public static NumberFormat getVariableFractionCoinFormatter(int fractionCount) {
        NumberFormat format = NumberFormat.getNumberInstance(Locale.CHINA);
        format.setMaximumFractionDigits(fractionCount);
        format.setGroupingUsed(false);
        return format;
    }

    public static NumberFormat getKbFormatter() {
        if (kbFormatter == null) {
            synchronized (FormatterUtil.class) {
                kbFormatter = NumberFormat.getNumberInstance(Locale.CHINA);
                kbFormatter.setMaximumFractionDigits(3);
            }
        }
        return kbFormatter;
    }

    public static NumberFormat getKbFormatterMode(RoundingMode roundingMode) {
        synchronized (FormatterUtil.class) {
            kbFormatter = NumberFormat.getNumberInstance(Locale.CHINA);
            kbFormatter.setMaximumFractionDigits(3);
            kbFormatter.setRoundingMode(roundingMode);
        }
        return kbFormatter;
    }

    //formatter整数
    public static String formatterInteger(int amount) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.CHINA);
        numberFormat.setGroupingUsed(false);
        return numberFormat.format(amount);
    }

    /**
     * 4位小数-向下取证
     */
    public static String formatterFourDown(double amount) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.CHINA);
        numberFormat.setGroupingUsed(false);
        numberFormat.setMaximumFractionDigits(4);
        numberFormat.setRoundingMode(RoundingMode.DOWN);
        return numberFormat.format(amount);
    }


    public static NumberFormat getCurrencyInputFormatter() {
        if (currencyInputFormatter == null) {
            synchronized (FormatterUtil.class) {
                currencyInputFormatter = NumberFormat.getNumberInstance(Locale.CHINA);
                // 法币只保留两位小数点，输入的时候
                currencyInputFormatter.setMaximumFractionDigits(2);
                currencyInputFormatter.setGroupingUsed(false);
            }
        }
        return currencyInputFormatter;
    }

    public static NumberFormat getPercentFormatter() {
        if (percentFormatter == null) {
            synchronized (FormatterUtil.class) {
                percentFormatter = NumberFormat.getPercentInstance(Locale.CHINA);
                //展示到小数点后两位
                percentFormatter.setMaximumFractionDigits(2);
                percentFormatter.setMinimumFractionDigits(2);
                percentFormatter.setGroupingUsed(false);

                // A little hack to add '+' to percent.
                DecimalFormat formatter = (DecimalFormat) percentFormatter;
                formatter.setPositivePrefix("+");
            }
        }
        return percentFormatter;
    }

    public static NumberFormat getPercentFormatterTwoDown() {
        if (percentFormatterDownOrUp == null) {
            synchronized (FormatterUtil.class) {
                percentFormatterDownOrUp = NumberFormat.getPercentInstance(Locale.CHINA);
                percentFormatterDownOrUp.setGroupingUsed(false);
                percentFormatterDownOrUp.setMaximumFractionDigits(2);
                percentFormatterDownOrUp.setMinimumFractionDigits(2);
                percentFormatterDownOrUp.setRoundingMode(RoundingMode.DOWN);
                DecimalFormat formatter = (DecimalFormat) percentFormatterDownOrUp;
                formatter.setPositivePrefix("+");
            }
        }
        return percentFormatterDownOrUp;
    }

    public static NumberFormat getPercentFormatterTwoDownNoSignDown() {
        if (percentNoSignFormatterTwo == null) {
            synchronized (FormatterUtil.class) {
                percentNoSignFormatterTwo = NumberFormat.getPercentInstance(Locale.CHINA);
                percentNoSignFormatterTwo.setGroupingUsed(false);
                percentNoSignFormatterTwo.setMaximumFractionDigits(2);
                percentNoSignFormatterTwo.setMinimumFractionDigits(2);
                percentNoSignFormatterTwo.setRoundingMode(RoundingMode.DOWN);
            }
        }
        return percentNoSignFormatterTwo;
    }

    //xx%,不保留小数,向下取整
    public static NumberFormat getPercentNoFractionFormatter() {
        if (percentNoFractionFormatter == null) {
            synchronized (FormatterUtil.class) {
                percentNoFractionFormatter = NumberFormat.getPercentInstance(Locale.CHINA);
                //不保留小数
                percentNoFractionFormatter.setGroupingUsed(false);
                percentNoFractionFormatter.setMaximumFractionDigits(0);
                percentNoFractionFormatter.setRoundingMode(RoundingMode.DOWN);
                DecimalFormat formatter = (DecimalFormat) percentNoFractionFormatter;
                formatter.setPositivePrefix("");
            }
        }
        return percentNoFractionFormatter;
    }

    public static NumberFormat getPercentNoSignFormatter() {
        if (percentNoSignFormatter == null) {
            synchronized (FormatterUtil.class) {
                percentNoSignFormatter = NumberFormat.getPercentInstance(Locale.CHINA);
                //最多展示到小数点后两位
                percentNoSignFormatter.setGroupingUsed(false);
                percentNoSignFormatter.setMaximumFractionDigits(2);
                DecimalFormat formatter = (DecimalFormat) percentNoSignFormatter;
                formatter.setPositivePrefix("");
            }
        }
        return percentNoSignFormatter;
    }

    public static NumberFormat getPercentNoSignTwoDecimalFormatter() {
        if (percentNoSignTwoDecimalFormatter == null) {
            synchronized (FormatterUtil.class) {
                percentNoSignTwoDecimalFormatter = NumberFormat.getPercentInstance(Locale.CHINA);
                //展示到小数点后两位
                percentNoSignTwoDecimalFormatter.setGroupingUsed(false);
                percentNoSignTwoDecimalFormatter.setMaximumFractionDigits(2);
                percentNoSignTwoDecimalFormatter.setMinimumFractionDigits(2);
                DecimalFormat formatter = (DecimalFormat) percentNoSignTwoDecimalFormatter;
                formatter.setPositivePrefix("");
            }
        }
        return percentNoSignTwoDecimalFormatter;
    }

    /**
     * 对时间戳进行更改
     * 单位---天
     * 时间戳--毫秒
     */
    public static long changeTimestamp(long time, int day) {
        if (calendarDay == null) {
            synchronized (FormatterUtil.class) {
                calendarDay = new GregorianCalendar();
            }
        }
        calendarDay.setTime(new Date(time));
        calendarDay.add(Calendar.DATE, day);
        return calendarDay.getTime().getTime();
    }

    /**
     * 对时间戳进行更改
     * 单位---分钟
     * 时间戳--秒
     */
    public static long changeTimeMinute(long time, int minute) {
        return time + minute * 60;
    }

    /**
     * 对时间戳进行更改
     * 单位---天
     * 时间戳--秒
     */
    public static long changeTimeDay(long time, int day) {
        return time + day * 60 * 60 * 24;
    }


    public static String formatDateTimeSec(long timeSec) {
        return formatDateTime(timeSec * 1000);
    }

    public static String formatDateTimeVice(long timeMillis) {
        if (YMDDateFormatVice == null) {
            synchronized (FormatterUtil.class) {
                YMDDateFormatVice = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
            }
        }
        YMDDateFormatVice.setTimeZone(TimeZone.getDefault());
        return YMDDateFormatVice.format(new Date(timeMillis));
    }

    public static String formatDateTimeWithDot(long timeMillis) {
        if (YMDDateFormatVice == null) {
            synchronized (FormatterUtil.class) {
                YMDDateFormatVice = new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA);
            }
        }
        YMDDateFormatVice.setTimeZone(TimeZone.getDefault());
        return YMDDateFormatVice.format(new Date(timeMillis));
    }

    public static String formatDateTimeSeparator(long timeMillis) {
        if (YMDDateFormatSeparator == null) {
            synchronized (FormatterUtil.class) {
                YMDDateFormatSeparator = new SimpleDateFormat("yy-MM-dd", Locale.CHINA);
            }
        }
        YMDDateFormatSeparator.setTimeZone(TimeZone.getDefault());
        return YMDDateFormatSeparator.format(new Date(timeMillis));
    }

    public static String formatDateTime(long timeMillis) {
        if (dateFormat == null) {
            synchronized (FormatterUtil.class) {
                dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            }
        }
        dateFormat.setTimeZone(TimeZone.getDefault());
        return dateFormat.format(new Date(timeMillis));
    }

    public static String formatMinuteTime(long timeMillis) {
        if (minuteDateFormat == null) {
            synchronized (FormatterUtil.class) {
                minuteDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
            }
        }
        minuteDateFormat.setTimeZone(TimeZone.getDefault());
        return minuteDateFormat.format(new Date(timeMillis));
    }

    public static String formatMonthTime(long timeMillis) {
        if (monthDateFormat == null) {
            synchronized (FormatterUtil.class) {
                monthDateFormat = new SimpleDateFormat("MM-dd", Locale.CHINA);
            }
        }
        monthDateFormat.setTimeZone(TimeZone.getDefault());
        return monthDateFormat.format(new Date(timeMillis));
    }

    public static String formatMonthTimeVice(long timeMillis) {
        if (monthDateFormatVice == null) {
            synchronized (FormatterUtil.class) {
                monthDateFormatVice = new SimpleDateFormat("M月d号", Locale.CHINA);
            }
        }
        monthDateFormatVice.setTimeZone(TimeZone.getDefault());
        return monthDateFormatVice.format(new Date(timeMillis));
    }


    public static String formatDayTime(long time) {
        if (dayDateFormat == null) {
            synchronized (FormatterUtil.class) {
                dayDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
            }
        }
        dayDateFormat.setTimeZone(TimeZone.getDefault());
        return dayDateFormat.format(new Date(time));
    }

    public static String formatLotteryProductTime(long time) {
        SimpleDateFormat dayDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        dayDateFormat.setTimeZone(TimeZone.getDefault());
        return dayDateFormat.format(new Date(time));
    }

    // 10：15
    public static String formatTimeWithHM(long time) {
        if (HMateFormat == null) {
            synchronized (FormatterUtil.class) {
                HMateFormat = new SimpleDateFormat("HH:mm", Locale.CHINA);
            }
        }
        HMateFormat.setTimeZone(TimeZone.getDefault());
        return HMateFormat.format(new Date(time));
    }

    //获得手机设置的时区，例如：中国标准时间
    public static String getTimeZoneLong() {
        return TimeZone.getDefault().getDisplayName(false, TimeZone.LONG);
    }

    //获得手机设置的时区，例如：GMT+08:00
    public static String getTimeZoneShort() {
        return TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT);
    }

    /**
     * //定价-金额位数(向上取整)
     * https://git.windimg.com/production/work-in-progress/issues/508--币种单价向上取整
     */
    public static String formatCurrencyNoSign(double amount) {
        final NumberFormat format = NumberFormat.getNumberInstance(Locale.CHINA);
        double amountNum = amount;
        if (amount < 0) {
            amount = -amount;
        }
        if (amount < 1) {
            format.setMaximumFractionDigits(6);
        } else if (amount < 100) {
            format.setMaximumFractionDigits(4);
        } else {
            format.setMaximumFractionDigits(2);
        }
        //最少展示到小数点后两位
        format.setMinimumFractionDigits(2);
        format.setGroupingUsed(false);
        format.setRoundingMode(RoundingMode.UP);//
        return format.format(amountNum);
    }

    //金额固定两位小数的显示方法
    public static String formatCurrencyNoSignTwoDecimal(double amount) {
        final NumberFormat format = NumberFormat.getNumberInstance(Locale.CHINA);
        format.setGroupingUsed(false);
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);
        return format.format(amount);
    }

    //金额最多两位小数的显示方法
    public static String formatCurrencyNoSignMaxTwoDecimal(double amount) {
        final NumberFormat format = NumberFormat.getNumberInstance(Locale.CHINA);
        format.setGroupingUsed(false);
        format.setMaximumFractionDigits(2);
        return format.format(amount);
    }

    public static String formatCoin(double count, RoundingMode roundingMode) {
        final NumberFormat format = getCoinFormatter();
        final RoundingMode oldRoundMode = format.getRoundingMode();
        format.setRoundingMode(roundingMode);
        final String coinCount = format.format(count);
        format.setRoundingMode(oldRoundMode);
        format.setGroupingUsed(false);
        return coinCount;
    }

    public static NumberFormat getCoinWithMaxFractionFormatter(RoundingMode roundingMode, int fraction) {
        final NumberFormat format = NumberFormat.getNumberInstance(Locale.CHINA);
        format.setMaximumFractionDigits(fraction);
        format.setRoundingMode(roundingMode);
        format.setGroupingUsed(false);
        return format;
    }

    public static String upperCase(final String text) {
        return text.toUpperCase(Locale.CHINA);
    }

    public static String lowerCase(final String text) {
        return text.toLowerCase(Locale.CHINA);
    }

    public static String formatDateTimeYM(long timeMillis) {

        if (YMDateFormat == null) {
            synchronized (FormatterUtil.class) {
                YMDateFormat = new SimpleDateFormat("yyyy-MM", Locale.CHINA);
            }
        }
        YMDateFormat.setTimeZone(TimeZone.getDefault());
        return YMDateFormat.format(new Date(timeMillis));
    }

    public static String formatDateTimeYMD(long timeMillis) {

        if (YMDDateFormat == null) {
            synchronized (FormatterUtil.class) {
                YMDDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            }
        }
        YMDDateFormat.setTimeZone(TimeZone.getDefault());
        return YMDDateFormat.format(new Date(timeMillis));
    }

    public static String formatDateTimeMD(long timeMillis) {

        if (MDDateFormat == null) {
            synchronized (FormatterUtil.class) {
                MDDateFormat = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA);
            }
        }
        MDDateFormat.setTimeZone(TimeZone.getDefault());
        return MDDateFormat.format(new Date(timeMillis));
    }

    // 普通数字，最多保留maxFraction位小数的formatter
    public static NumberFormat getVariableFractionNumberFormatter(int maxFraction) {
        NumberFormat format = NumberFormat.getNumberInstance(Locale.CHINA);
        format.setMaximumFractionDigits(maxFraction);
        //设置了以后不会有千分位，如果不设置，默认是有的
        format.setGroupingUsed(false);
        return format;
    }

    // 设置整数最大、小数最大
    public static NumberFormat getMaxDigitNumberFormatter(int maximumInteger, int maximumFraction, boolean isRoundingModeUp) {
        NumberFormat format = NumberFormat.getNumberInstance(Locale.CHINA);
        //设置了以后不会有千分位，如果不设置，默认是有的
        format.setGroupingUsed(false);
        format.setMaximumIntegerDigits(maximumInteger);
        format.setMaximumFractionDigits(maximumFraction);
        format.setRoundingMode(isRoundingModeUp ? RoundingMode.UP : RoundingMode.DOWN);
        DecimalFormat decimalFormat = (DecimalFormat) format;//开启了NumberFormat底层的DecimalFormat的BigDecimal模式
        decimalFormat.setParseBigDecimal(true);
        return format;
    }

    // 设置整数最大、小数最大、四舍五入
    public static NumberFormat getMaxDigitNumberFormatter(int maximumInteger, int maximumFraction) {
        NumberFormat format = NumberFormat.getNumberInstance(Locale.CHINA);
        //设置了以后不会有千分位，如果不设置，默认是有的
        format.setGroupingUsed(false);
        format.setMaximumIntegerDigits(maximumInteger);
        format.setMaximumFractionDigits(maximumFraction);
        DecimalFormat decimalFormat = (DecimalFormat) format;//开启了NumberFormat底层的DecimalFormat的BigDecimal模式
        decimalFormat.setParseBigDecimal(true);
        return format;
    }

    // 设置整数最大、小数最大 ,小数最小
    public static NumberFormat getMaxMinDigitNumberFormatter(int maximumInteger, int maximumFraction, int minimumFraction, boolean isRoundingModeUp) {
        NumberFormat format = NumberFormat.getNumberInstance(Locale.CHINA);
        //设置了以后不会有千分位，如果不设置，默认是有的
        format.setGroupingUsed(false);
        format.setMaximumIntegerDigits(maximumInteger);
        format.setMaximumFractionDigits(maximumFraction);
        format.setMinimumFractionDigits(minimumFraction);
        format.setRoundingMode(isRoundingModeUp ? RoundingMode.UP : RoundingMode.DOWN);
        DecimalFormat decimalFormat = (DecimalFormat) format;//开启了NumberFormat底层的DecimalFormat的BigDecimal模式
        decimalFormat.setParseBigDecimal(true);
        return format;
    }

    // 设置整数最大、小数最大 加EPSION
    public static String getMaxDigitNumberFormatter(double amount, int maximumInteger, int maximumFraction, boolean isRoundingModeUp) {
        return getMaxDigitNumberFormatter(maximumInteger, maximumFraction, isRoundingModeUp).format(isRoundingModeUp ? amount - EPSION : amount + EPSION);
    }

    /**
     * 金额（数量折合CNY）的规范：
     * 2018年12月11日 各操作流程数字格式规范 9+2
     * https://git.windimg.com/production/work-in-progress/issues/434
     * 向上取整
     */
    public static String getCoinFormatterIsCNY(double cny) {
        NumberFormat format = NumberFormat.getNumberInstance(Locale.CHINA);
        //设置了以后不会有千分位，如果不设置，默认是有的
        format.setGroupingUsed(false);
        format.setMaximumIntegerDigits(9);
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);
        format.setRoundingMode(RoundingMode.UP);
        return format.format(cny);
    }

    /**
     * 金额（数量折合CNY）的规范：
     * 2018年12月11日 各操作流程数字格式规范 9+2
     * https://git.windimg.com/production/work-in-progress/issues/434
     * 向上取整
     */
    public static NumberFormat getCoinFormatterIsCNY() {
        NumberFormat format = NumberFormat.getNumberInstance(Locale.CHINA);
        //设置了以后不会有千分位，如果不设置，默认是有的
        format.setGroupingUsed(false);
        format.setMaximumIntegerDigits(9);
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);
        format.setRoundingMode(RoundingMode.UP);
        return format;
    }

    /**
     * 金额（数量折合CNY）的规范：
     * 向下取整
     */
    public static NumberFormat getCoinFormatterIsCNYDown() {
        NumberFormat format = NumberFormat.getNumberInstance(Locale.CHINA);
        //设置了以后不会有千分位，如果不设置，默认是有的
        format.setGroupingUsed(false);
        format.setMaximumIntegerDigits(9);
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);
        format.setRoundingMode(RoundingMode.DOWN);
        return format;
    }

    /**
     * 数量（虚拟币数量）的规范：
     * 2018年12月11日 各操作流程数字格式规范  10+8  首页 资产列表页 币种详情页 币种选择页  行情页  交易详情页
     * https://git.windimg.com/production/work-in-progress/issues/434
     * 币种数量-向下取整
     */
    public static NumberFormat getCoinFormatterIsAMOUNT(double unitPrice) {
        if (unitPrice >= 100) {
            return getMaxDigitNumberFormatter(6, 8, false);
        } else if (unitPrice >= 0 && unitPrice < 100) {
            return getMaxDigitNumberFormatter(10, 8, false);
        }
        return getMaxDigitNumberFormatter(10, 8, false);
    }

    /**
     * 币种数量-向上取整
     */
    public static NumberFormat getCoinFormatterIsAMOUNTUp(double unitPrice) {
        if (unitPrice >= 100) {
            return getMaxDigitNumberFormatter(6, 8, true);
        } else if (unitPrice >= 0 && unitPrice < 100) {
            return getMaxDigitNumberFormatter(10, 8, true);
        }
        return getMaxDigitNumberFormatter(10, 8, true);
    }

    /**
     * 收益-向上取整 最多8位小数 最少8位小数
     */
    public static NumberFormat getIncomFormatterIsAMOUNTUp(double unitPrice) {
        if (unitPrice >= 100) {
            return getMaxMinDigitNumberFormatter(6, 8, 8, false);
        } else if (unitPrice >= 0 && unitPrice < 100) {
            return getMaxMinDigitNumberFormatter(10, 8, 8, false);
        }
        return getMaxMinDigitNumberFormatter(10, 8, 8, false);
    }

    /**
     * 收益-向上取整 最多8位小数 最少8位小数
     */
    public static NumberFormat getIncom12FormatterIsAMOUNTUp(double unitPrice) {
        if (unitPrice >= 100) {
            return getMaxMinDigitNumberFormatter(6, 12, 12, false);
        } else if (unitPrice >= 0 && unitPrice < 100) {
            return getMaxMinDigitNumberFormatter(10, 12, 12, false);
        }
        return getMaxMinDigitNumberFormatter(10, 12, 12, false);
    }

    /**
     * Add EPSILON before round down, to avoid float accuracy problem.
     *
     * @param count
     * @param unitPrice
     * @return
     */
    public static String formatCoinIsAMOUNT(double count, double unitPrice) {
        final NumberFormat format = getCoinFormatterIsAMOUNT(unitPrice);
        if (count == 0) {
            return format.format(count);
        }
        return format.format(count + EPSION);
    }

    /**
     * Sub EPSILON before round up, to avoid float accuracy problem.
     *
     * @param count
     * @param unitPrice
     * @return
     */
    public static String formatCoinIsAMOUNTUp(double count, double unitPrice) {
        final NumberFormat format = getCoinFormatterIsAMOUNTUp(unitPrice);
        if (count <= EPSION) {
            return format.format(count);
        }
        return format.format(count - EPSION);
    }

    /**
     * 向上取整 保留4位  带有百分号
     *
     * @return
     */
    public static NumberFormat getPercentNoSignFormatterUp() {
        if (percentFormatterUp == null) {
            synchronized (FormatterUtil.class) {
                percentFormatterUp = NumberFormat.getPercentInstance(Locale.CHINA);
                percentFormatterUp.setGroupingUsed(false);
                percentFormatterUp.setMaximumFractionDigits(2);//最多展示到小数点后两位
                percentFormatterUp.setRoundingMode(RoundingMode.UP);
                DecimalFormat formatter = (DecimalFormat) percentFormatterUp;
                formatter.setPositivePrefix("");
            }
        }
        return percentFormatterUp;
    }


    /**
     * 未发放预估收益 向上取整 最多8位 最小8位
     *
     * @param count
     * @param unitPrice
     * @return
     */
    public static String formatIncomIsAMOUNTDown(double count, double unitPrice) {
        final NumberFormat format = getIncomFormatterIsAMOUNTUp(unitPrice);
        if (count <= EPSION) {
            return format.format(count);
        }
        return format.format(count - EPSION);
    }

    /**
     * 未发放预估收益 向上取整 最多12位 最小12位
     *
     * @param count
     * @param unitPrice
     * @return
     */
    public static String formatIncom12IsAMOUNTDown(double count, double unitPrice) {
        final NumberFormat format = getIncom12FormatterIsAMOUNTUp(unitPrice);
        if (count <= EPSION) {
            return format.format(count);
        }
        return format.format(count - EPSION);
    }

    /**
     * 单位亿、K
     * 大等于一亿----单位亿
     * else----单位K
     */
    public static String formatBillion(double amount) {
        if (amount >= 10000000) {
            return getCoinFormatterIsCNYDown().format(amount / BILLION) + "亿";
        } else if (amount >= 1000 && amount < 10000000) {
            return getCoinFormatterIsCNYDown().format(amount / THOUSAND) + "K";
        } else {
            return getCoinFormatterIsCNYDown().format(amount);
        }
    }


    /**
     * 针对行情 询价-计价 的NumberFormat
     *
     * @param baseCoin 询价币
     * @param qutoCoin 计价币
     *                 https://git.windimg.com/production/work-in-progress/uploads/28c5367d07c5b221b1209d5c41c03c69/%E8%A1%8C%E6%83%85%E9%A1%B5%E6%94%B9%E7%89%88.png
     */
    public static NumberFormat getMarketNumberFormat(String baseCoin, String qutoCoin) {
        switch (qutoCoin) {
            case "tcny":
                return getMaxMinDigitNumberFormatter(10, 2, 2, false);
            case "usdt":
                return getQutoCoinUSTD(baseCoin);
            case "btc":
                return getQutoCoinBTC(baseCoin);
            case "eth":
                return getQutoCoinETH(baseCoin);
        }
        return getMaxMinDigitNumberFormatter(10, 2, 2, false);
    }

    private static NumberFormat getQutoCoinUSTD(String baseCoin) {
        switch (baseCoin) {
            case "eth":
            case "bch":
            case "ltc":
            case "neo":
            case "btc":
            case "dash":
                return getCoinFormatterIsCNYDown();
            case "eos":
            case "xrp":
            case "etc":
            case "ont":
                return getMaxMinDigitNumberFormatter(10, 4, 4, false);
        }
        return getCoinFormatterIsCNYDown();
    }

    private static NumberFormat getQutoCoinBTC(String baseCoin) {
        switch (baseCoin) {
            case "eth":
            case "bch":
            case "ltc":
            case "etc":
            case "neo":
            case "dash":
                return getMaxMinDigitNumberFormatter(10, 6, 6, false);
            case "eos":
            case "xrp":
            case "ont":
                return getMaxMinDigitNumberFormatter(10, 8, 8, false);
        }
        return getCoinFormatterIsCNYDown();
    }

    private static NumberFormat getQutoCoinETH(String baseCoin) {
        switch (baseCoin) {
            case "eos":
            case "etc":
            case "neo":
            case "ont":
                return getMaxMinDigitNumberFormatter(10, 6, 6, false);
            case "ltc":
            case "dash":
                return getMaxMinDigitNumberFormatter(10, 5, 5, false);
            case "xrp":
                return getMaxMinDigitNumberFormatter(10, 8, 8, false);
        }
        return getCoinFormatterIsCNYDown();
    }

}
