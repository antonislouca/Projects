"""
This file consists of functions that call each prediction algorihtm
this functions use the parameters found using the grid seach 
file. Thus algorithms are called for prediction using the optimal parameters.
"""
# @-------------------IMPORTS-----------------------

import matplotlib.pyplot as plt
from numpy import sqrt
from sklearn.metrics import mean_squared_error, r2_score, accuracy_score, mean_squared_log_error
from statistics import mean, stdev
import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split
# algorithms import
from sklearn.linear_model import LinearRegression, Lasso
from sklearn.svm import SVR
from sklearn.ensemble import RandomForestRegressor
from sklearn.tree import DecisionTreeRegressor
from sklearn.preprocessing import PolynomialFeatures
from sklearn.ensemble import GradientBoostingRegressor
import lightgbm as lgb
import seaborn as sns


# @-------------------FUNCTIONS---------------------


def linear_reg(X, Y):
    print("\nTraining Linear Regression...")

    # split data to train (70%) test (30%),
    x_train, x_test, y_train, y_test = train_test_split(
        X, Y, test_size=0.3)  # train 70% and test=30%

    # create linear regression algo
    lr = LinearRegression(copy_X=True, fit_intercept=True)
    lr = lr.fit(x_train, y_train)
    y_pred_lr = lr.predict(x_test)

    # get mean sqr
    meanSQR = mean_squared_error(y_test, y_pred_lr)  # here y_Val or y_test

    # get variance score
    variance = r2_score(y_test, y_pred_lr)

    evaluation = 1 - np.sqrt(np.square(np.log10(abs(y_pred_lr + 1)) -
                                       np.log10(abs(y_test + 1))).mean())
    print("Evaluation: ", evaluation)
    return evaluation


def support_vector_regression(X, Y):
    print("\nTraining support vector regression (SVR) ...")

    # split data to train (70%) test (30%),
    x_train, x_test, y_train, y_test = train_test_split(
        X, Y, test_size=0.3)  # train 70% and test=30%

    svr = SVR(C=15, coef0=10, degree=4, kernel='poly',
              gamma='scale').fit(x_train, y_train)  # train model
    # predict
    y_pred_svr = svr.predict(x_test)

    # metrics:
    # get mean sqr
    meanSQR = mean_squared_error(y_test, y_pred_svr)  # here y_Val or y_test

    # get variance score
    variance = r2_score(y_test, y_pred_svr)
    # print("meanSQR= {}\nvariance= {}".format(meanSQR, variance))
    evaluation = 1 - \
        np.sqrt(np.square(np.log10(y_pred_svr + 1) - np.log10(y_test + 1)).mean())
    print("Evaluation: ", evaluation)
    return evaluation


def lasso_Reggression(X, Y):  # note: can pass parameter for iterations
    print("\nTraining Lasso Regression...")
    # split data to train (70%) test (30%),
    x_train, x_test, y_train, y_test = train_test_split(
        X, Y, test_size=0.3)  # train 70% and test=30%

    # Fit regression model
    lassoReg = Lasso(alpha=0.2807216203941176)
    lassoReg = lassoReg.fit(x_train, y_train)
    y_pred_lasso = lassoReg.predict(x_test)

    # metrics:
    # get mean sqr
    meanSQR = mean_squared_error(y_test, y_pred_lasso)  # here y_Val or y_test

    # get variance score
    variance = r2_score(y_test, y_pred_lasso)
    evaluation = 1 - \
        np.sqrt(np.square(np.log10(abs(y_pred_lasso + 1)) -
                          np.log10(abs(y_test + 1))).mean())
    print("Evaluation: ", evaluation)
    return evaluation


def random_forest(X, Y):
    print("\nTraining Random forest Regression...")
    # split data to train (70%) test (30%),
    x_train, x_test, y_train, y_test = train_test_split(
        X, Y, test_size=0.3)  # train 70% and test=30%

    # create and set depth
    rfr = RandomForestRegressor(max_depth=None, max_features=None, bootstrap=True,
                                min_samples_leaf=1, min_samples_split=8, n_estimators=200)

    # train and predict
    rfr = rfr.fit(x_train, y_train)
    y_pred_rfr = rfr.predict(x_test)

    # metrics:
    # get mean sqr
    meanSQR = mean_squared_error(y_test, y_pred_rfr)  # here y_Val or y_test

    # get variance score
    variance = r2_score(y_test, y_pred_rfr)
    evaluation = 1 - \
        np.sqrt(np.square(np.log10(y_pred_rfr + 1) - np.log10(y_test + 1)).mean())
    print("Evaluation: ", evaluation)
    return evaluation


def show_values(value, predicted) -> str:
    values = ""
    for x, y in np.c_[value, predicted]:
        values = values + str(x) + " -----> " + str(y) + "\n"
    return values


def decision_tree(X, Y):
    print("\nTraining Decission Tree Regression...")
    # split data to train (70%) test (30%),
    x_train, x_test, y_train, y_test = train_test_split(
        X, Y, test_size=0.3)  # train 70% and test=30%

    dtr = DecisionTreeRegressor(max_depth=None,
                                max_leaf_nodes=None, min_samples_leaf=1, min_samples_split=20)

    dtr.fit(x_train, y_train)
    y_pred_dtr = dtr.predict(x_test)

    # metrics:
    # get mean sqr
    meanSQR = mean_squared_error(y_test, y_pred_dtr)  # here y_Val or y_test

    # get variance score
    variance = r2_score(y_test, y_pred_dtr)
    evaluation = 1 - \
        np.sqrt(np.square(np.log10(y_pred_dtr + 1) - np.log10(y_test + 1)).mean())
    print("Evaluation: ", evaluation)
    return evaluation


def polynomial_reg(X, Y):
    print("\nTraining Polynomial Regression...")
    # split data to train (70%) test (30%),
    x_train, x_test, y_train, y_test = train_test_split(
        X, Y, test_size=0.3)  # train 70% and test=30%

    plr = PolynomialFeatures(degree=2)  # degree variable?
    x_poly_train = plr.fit_transform(x_train)
    x_val_poly = plr.fit_transform(x_test)
    plr = LinearRegression(copy_X=True, fit_intercept=False)

    # train and predict
    plr.fit(x_poly_train, y_train)

    y_pred_plr = plr.predict(x_val_poly)

    # metrics:
    # get mean sqr
    meanSQR = mean_squared_error(y_test, y_pred_plr)

    # get variance score
    variance = r2_score(y_test, y_pred_plr)
    # print("meanSQR= {}\nvariance= {}".format(meanSQR, variance))
    evaluation = 1 - np.sqrt(np.square(np.log10(abs(y_pred_plr + 1)) -
                                       np.log10(abs(y_test + 1))).mean())
    print("Evaluation: ", evaluation)
    return evaluation


"""
Best values but we modified them: learning_rate=0.3, max_depth=7, min_samples_split=30,
                          n_estimators=200, random_state=0
"""


def GBR(X, Y):
    print("\nTraining Gradient Boost Regression...")
    # split data to train (70%) test (30%),
    x_train, x_test, y_train, y_test = train_test_split(
        X, Y, test_size=0.3)  # train 70% and test=30%

    gb = GradientBoostingRegressor(
        learning_rate=0.3,
        n_estimators=380,
        subsample=1.0,
        criterion='friedman_mse',
        min_samples_split=30,
        min_samples_leaf=1,
        min_weight_fraction_leaf=0.0,
        max_depth=7,
        min_impurity_decrease=0.0,
        init=None,
        random_state=0,
        max_features=None,
        alpha=0.9,
        max_leaf_nodes=None,
        warm_start=False)
    gb.fit(x_train, y_train)
    y_pred4 = gb.predict(x_test)
    evaluation = 1 - \
        np.sqrt(np.square(np.log10(abs(y_pred4 + 1)) -
                np.log10(abs(y_test + 1))).mean())
    print("Evaluation: ", evaluation)
    return evaluation


def Lgb(X, Y):
    print("\nTraining LGB Regression...")
    x_train, x_test, y_train, y_test = train_test_split(
        X, Y, test_size=0.3)  # train 70% and test=30%

    train_data = lgb.Dataset(x_train, label=y_train)
    test_data = lgb.Dataset(x_test, label=y_test)

    param = {'objective': 'regression',
             'boosting': 'gbdt',
             'num_iterations': 3000,
             'learning_rate': 0.06,
             'num_leaves': 40,
             'max_depth': 24,
             'min_data_in_leaf': 11,
             'max_bin': 4,
             'metric': 'l2_root',
             'verbose': -1,

             }
    lgbm = lgb.train(params=param,

                     train_set=train_data,
                     valid_sets=[test_data], verbose_eval=False)

    y_pred2 = lgbm.predict(x_test)
    evaluation = 1 - \
        np.sqrt(np.square(np.log10(y_pred2 + 1) - np.log10(y_test + 1)).mean())
    print("Evaluation: ", evaluation)
    return evaluation


# @-------------------MAIN--------------------------
df = pd.read_csv('dataset_filtered.csv')
Y = df.values[:, -1]  # target/price
algos = ['LGB', 'GBR', 'LR', 'Lasso',
         'RFR', 'DTR', 'SVR', 'PR']

print("Run with feature selection SFS")
X = df.values[:, [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13,
                  14, 17, 18, 21, 24, 25, 26, 27, 29, 30]]  # features

#!DONOT DELETE YET keep range SFS: '1', '2', '3', '5', '6', '7', '9', '11', '19', '22', '25', '27', '28' <---
# keep range SBS: 1, 2, 3, 4, 5, 6, 9, 11, 13, 22, 23, 25, 27, 28

values = []
# DONE
values.append(Lgb(X, Y))
values.append(GBR(X, Y))
values.append(linear_reg(X, Y))
values.append(lasso_Reggression(X, Y))
values.append(random_forest(X, Y))
values.append(decision_tree(X, Y))
values.append(support_vector_regression(X, Y))
values.append(polynomial_reg(X, Y))


print("\nRun with feature selection SBS....")
X = df.values[:, [0, 1, 2, 4, 6, 7, 8, 9, 10, 11,
                  12, 13, 14, 18, 19, 21, 22, 25, 26, 27, 29, 30]]
values_sbs = []
# DONE
values_sbs.append(Lgb(X, Y))
values_sbs.append(GBR(X, Y))
values_sbs.append(linear_reg(X, Y))
values_sbs.append(lasso_Reggression(X, Y))
values_sbs.append(random_forest(X, Y))
values_sbs.append(decision_tree(X, Y))
values_sbs.append(support_vector_regression(X, Y))
values_sbs.append(polynomial_reg(X, Y))


print("\nRun without feature selection....")
X = df.values[:, :-1]  # no feature selection

values_NO = []
# DONE
values_NO.append(Lgb(X, Y))
values_NO.append(GBR(X, Y))
values_NO.append(linear_reg(X, Y))
values_NO.append(lasso_Reggression(X, Y))
values_NO.append(random_forest(X, Y))
values_NO.append(decision_tree(X, Y))
values_NO.append(support_vector_regression(X, Y))
values_NO.append(polynomial_reg(X, Y))


# graphs
plotdata = pd.DataFrame({

    "SFS": values,

    "SBS": values_sbs,

    "NO FEATURE SELECTION": values_NO},

    index=algos)

plotdata['SFS'] = plotdata['SFS'].apply(lambda val: round(val, 2))
plotdata['SBS'] = plotdata['SBS'].apply(lambda val: round(val, 2))
plotdata['NO FEATURE SELECTION'] = plotdata['NO FEATURE SELECTION'].apply(
    lambda val: round(val, 2))

print(plotdata['SBS'])
print(plotdata['SFS'])
print(plotdata['NO FEATURE SELECTION'])
plotdata.plot(kind="bar", figsize=(10, 10), ylabel="Scores")


# plt.ylabel("Scores")
plt.xticks(rotation=30, horizontalalignment="center")

# for index, value in enumerate(values):
#     plt.text(index, value, str(round(value, 2)))

plt.legend(loc='best')
plt.show()

# plotdata.to_excel(r'output.xlsx')
