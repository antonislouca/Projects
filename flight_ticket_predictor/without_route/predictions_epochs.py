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


# @-------------------FUNCTIONS---------------------
"""
Training Linear Regression...

-----------------Results from Grid Search-----------------

 The best estimator across ALL searched params:
 LinearRegression(fit_intercept=False)

 The best score across ALL searched params:
 0.7022335416403943

 The best parameters across ALL searched params:
 {'copy_X': True, 'fit_intercept': False}
"""


def linear_reg(X, Y):

    # split data to train (70%) test (30%),
    x_train, x_test, y_train, y_test = train_test_split(
        X, Y, test_size=0.3)  # train 70% and test=30%

    # create linear regression algo

    lr.fit(x_train, y_train)
    y_pred_lr = lr.predict(x_test)

    # get mean sqr
    meanSQR = mean_squared_error(y_test, y_pred_lr)  # here y_Val or y_test

    # get variance score
    variance = r2_score(y_test, y_pred_lr)

    evaluation = 1 - \
        np.sqrt(np.square(np.log10(abs(y_pred_lr + 1)) -
                np.log10(abs(y_test + 1))).mean())

    return evaluation


def support_vector_regression(X, Y):

    # split data to train (70%) test (30%),
    x_train, x_test, y_train, y_test = train_test_split(
        X, Y, test_size=0.3)  # train 70% and test=30%
    svr.fit(x_train, y_train)
    # predict
    y_pred_svr = svr.predict(x_test)

    # metrics:
    # get mean sqr
    meanSQR = mean_squared_error(y_test, y_pred_svr)  # here y_Val or y_test

    # get variance score
    variance = r2_score(y_test, y_pred_svr)
    evaluation = 1 - \
        np.sqrt(np.square(np.log10(y_pred_svr + 1) - np.log10(y_test + 1)).mean())

    return evaluation


"""
----------------Results from Grid Search for normalize=  False  ----------------

 The best estimator across ALL searched params:
 Lasso(alpha=0.2807216203941176)

 The best score across ALL searched params:
 0.7094968338999695

 The best parameters across ALL searched params:
 {'alpha': 0.2807216203941176}

"""


def lasso_Reggression(X, Y):  # note: can pass parameter for iterations

    # split data to train (70%) test (30%),
    x_train, x_test, y_train, y_test = train_test_split(
        X, Y, test_size=0.3)  # train 70% and test=30%

    # Fit regression model

    lassoReg.fit(x_train, y_train)
    y_pred_lasso = lassoReg.predict(x_test)

    # metrics:
    # get mean sqr
    meanSQR = mean_squared_error(y_test, y_pred_lasso)  # here y_Val or y_test

    # get variance score
    variance = r2_score(y_test, y_pred_lasso)
    evaluation = 1 - \
        np.sqrt(np.square(np.log10(abs(y_pred_lasso + 1)) -
                np.log10(abs(y_test + 1))).mean())

    return evaluation


"""
Training Radnom forest Regression...

-----------------Results from Grid Search-----------------

 The best estimator across ALL searched params:
 RandomForestRegressor(
     max_features=None, min_samples_split=8, n_estimators=1000)

 The best score across ALL searched params:
 0.8717203711248602

 The best parameters across ALL searched params:
 {'bootstrap': True, 'max_depth': None, 'max_features': None,
     'min_samples_leaf': 1, 'min_samples_split': 8, 'n_estimators': 1000}
"""


def random_forest(X, Y):

    # split data to train (70%) test (30%),
    x_train, x_test, y_train, y_test = train_test_split(
        X, Y, test_size=0.3)  # train 70% and test=30%

    # train and predict
    rfr.fit(x_train, y_train)
    y_pred_rfr = rfr.predict(x_test)

    # metrics:
    # get mean sqr
    meanSQR = mean_squared_error(y_test, y_pred_rfr)  # here y_Val or y_test

    # get variance score
    variance = r2_score(y_test, y_pred_rfr)
    evaluation = 1 - \
        np.sqrt(np.square(np.log10(y_pred_rfr + 1) - np.log10(y_test + 1)).mean())

    return evaluation


def show_values(value, predicted) -> str:
    values = ""
    for x, y in np.c_[value, predicted]:
        values = values + str(x) + " -----> " + str(y) + "\n"
    return values


"""
Training Decission Tree Regression...

-----------------Results from Grid Search-----------------

 The best estimator across ALL searched params:
 DecisionTreeRegressor(min_samples_split=20)

 The best score across ALL searched params:
 0.8261524396587767

 The best parameters across ALL searched params:
 {'criterion': 'mse', 'max_depth': None, 'max_leaf_nodes': None,
     'min_samples_leaf': 1, 'min_samples_split': 20}

"""


def decision_tree(X, Y):

    # split data to train (70%) test (30%),
    x_train, x_test, y_train, y_test = train_test_split(
        X, Y, test_size=0.3)  # train 70% and test=30%

    dtr.fit(x_train, y_train)
    y_pred_dtr = dtr.predict(x_test)

    # metrics:
    # get mean sqr
    meanSQR = mean_squared_error(y_test, y_pred_dtr)  # here y_Val or y_test

    # get variance score
    variance = r2_score(y_test, y_pred_dtr)
    evaluation = 1 - \
        np.sqrt(np.square(np.log10(y_pred_dtr + 1) - np.log10(y_test + 1)).mean())

    return evaluation


"""
Training Polynomial Regression...

----------Results from Grid Search for degree=  2 -------

 The best estimator across ALL searched params:
 LinearRegression(fit_intercept=False)

 The best score across ALL searched params:
 -118.48776383097463

 The best parameters across ALL searched params:
 {'copy_X': True, 'fit_intercept': False}
"""


def polynomial_reg(X, Y):

    # split data to train (70%) test (30%),
    x_train, x_test, y_train, y_test = train_test_split(
        X, Y, test_size=0.3)  # train 70% and test=30%
    plr2 = PolynomialFeatures(degree=2)  # degree variable?
    x_poly_train = plr2.fit_transform(x_train)
    x_val_poly = plr2.fit_transform(x_test)

    # train and predict
    plr.fit(x_poly_train, y_train)

    y_pred_plr = plr.predict(x_val_poly)

    # metrics:
    # get mean sqr
    meanSQR = mean_squared_error(y_test, y_pred_plr)

    # get variance score
    variance = r2_score(y_test, y_pred_plr)
    evaluation = 1 - np.sqrt(np.square(np.log10(abs(y_pred_plr + 1)) -
                                       np.log10(abs(y_test + 1))).mean())

    return evaluation


"""
Training GBR Regression...

 The best estimator across ALL searched params:
 GradientBoostingRegressor(learning_rate=0.3, max_depth=7, min_samples_split=30,
                          n_estimators=200, random_state=0)

 The best score across ALL searched params:
 0.8774318700676857

 The best parameters across ALL searched params:
 {'alpha': 0.9, 'criterion': 'friedman_mse', 'init': None,
 'learning_rate': 0.3, 'max_depth': 7, 'max_features': None, 'max_leaf_nodes': None,
 'min_impurity_decrease': 0.0, 'min_samples_leaf': 1, 'min_samples_split': 30,
 'min_weight_fraction_leaf': 0.0, 'n_estimators': 200, 'random_state': 0,
 'subsample': 1.0, 'warm_start': False}
 NOTE: we added our own params instead of gridsearch
 """


def GBR(X, Y):

    # split data to train (70%) test (30%),
    x_train, x_test, y_train, y_test = train_test_split(
        X, Y, test_size=0.3)  # train 70% and test=30%

    gb.fit(x_train, y_train)
    y_pred4 = gb.predict(x_test)
    evaluation = 1 - \
        np.sqrt(np.square(np.log10(y_pred4 + 1) - np.log10(y_test + 1)).mean())

    return evaluation


def Lgb(X, Y):

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

    return evaluation


# @-------------------MAIN--------------------------
df = pd.read_csv(r'dataset_filtered.csv')
Y = df.values[:, -1]  # target/price
algos = ['LGB', 'GBR', 'LR', 'Lasso',
         'RFR', 'DTR', 'SVR', 'PR']
lr = LinearRegression(copy_X=True, fit_intercept=False)
svr = SVR(C=15, coef0=10, degree=4, kernel='poly',
          gamma='scale')  # train model
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
plr = LinearRegression(copy_X=True, fit_intercept=False)
dtr = DecisionTreeRegressor(max_depth=None,
                            max_leaf_nodes=None, min_samples_leaf=1, min_samples_split=20)
# create and set depth
rfr = RandomForestRegressor(max_depth=None, max_features=None, bootstrap=True,
                            min_samples_leaf=1, min_samples_split=8, n_estimators=1000)
lassoReg = Lasso(alpha=0.2807216203941176)

# X = df.values[:, [1, 2, 3, 5, 6, 7, 8, 9, 10, 15, 18, 24, 26, 27, 32, 33,
#   34, 36, 37, 38, 41, 46, 51, 53, 54, 56, 57, 61, 62, 64, 71]]  # features
#!DONOT DELETE YET keep range SFS: 1, 2, 3, 5, 6, 7, 8, 9, 10, 15, 18, 24, 26, 27, 32, 33,34, 36, 37, 38, 41, 46, 51, 53, 54, 56, 57, 61, 62, 64, 71

# keep range SFS: '1', '2', '3', '5', '6', '7', '9', '11', '19', '22', '25', '27', '28' <---
# keep range SBS: 1, 2, 3, 4, 5, 6, 9, 11, 13, 22, 23, 25, 27, 28

for i in range(30):
    print("Epoch", i)
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
