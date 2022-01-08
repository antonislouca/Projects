
# @-------------------IMPORTS-----------------------

import matplotlib.pyplot as plt
from sklearn.metrics import mean_squared_error, r2_score, mean_squared_log_error
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
from sklearn.model_selection import GridSearchCV
from sklearn.ensemble import GradientBoostingRegressor

"""
This file contains functions that perform grid search for all the 
different algorithms we chose.
The goal of this is to find the optimal parameters for the algorithms
 so we can get more accurate predictions.
"""

# @-------------------FUNCTIONS---------------------

# @functions that use grid search:


def grid_linear_reg(X, Y):
    print("\nTraining Linear Regression...")

    # split data to train (70%) test (30%),
    x_train, x_test, y_train, y_test = train_test_split(
        X, Y, test_size=0.3)  # train 70% and test=30%

    # create linear regression algo
    lr = LinearRegression()

    parameters = {'fit_intercept': [True, False], 'copy_X': [True, False]}
    grid = GridSearchCV(lr, parameters, cv=5, n_jobs=-1)
    grid.fit(x_train, y_train)
    print("\n-----------------Results from Grid Search-----------------")
    print("\n The best estimator across ALL searched params:\n",
          grid.best_estimator_)
    print("\n The best score across ALL searched params:\n",
          grid.best_score_)
    print("\n The best parameters across ALL searched params:\n",
          grid.best_params_)


def grid_support_vector_regression(X, Y):
    print("\nTraining support vector regression (SVR) ...")
    # split data to train (70%) test (30%),
    x_train, x_test, y_train, y_test = train_test_split(
        X, Y, test_size=0.3)  # train 70% and test=30%

    svr = SVR().fit(x_train, y_train)  # train model
    # predict
    """
      param = {'kernel': ('linear', 'poly', 'rbf', 'sigmoid'), 'C': [5, 10,15], 'degree': [
        3, 4], 'coef0': [0.01,  0.5], 'gamma': ('auto', 'scale')}

         param = {'kernel': ('linear', 'poly', 'rbf', 'sigmoid'), 'C': [5, 10, 15], 'degree': [
        3, 4], 'coef0': [0.5, 10], 'gamma': ('auto', 'scale')}

         param = {'kernel': ('linear', 'poly', 'rbf', 'sigmoid'), 'C': [20], 'degree': [
        4, 6], 'coef0': [10, 15], 'gamma': ('auto', 'scale')}
    grid_search = GridSearchCV(estimator=svr, param_grid=param,
                               cv=5, n_jobs=-1, verbose=2)
    """
    param = {'kernel': ('linear', 'poly', 'rbf', 'sigmoid'), 'C': [5, 10, 15], 'degree': [
        3, 4], 'coef0': [0.5, 10], 'gamma': ('auto', 'scale')}
    grid_search = GridSearchCV(estimator=svr, param_grid=param,
                               cv=5, n_jobs=-1)
    grid_search.fit(x_train, y_train)

    # print results
    print("\n-----------------Results from Grid Search-----------------")
    print("\n The best estimator across ALL searched params:\n",
          grid_search.best_estimator_)
    print("\n The best score across ALL searched params:\n",
          grid_search.best_score_)
    print("\n The best parameters across ALL searched params:\n",
          grid_search.best_params_)


def grid_random_forest(X, Y):
    print("\nTraining Random forest Regression...")
    # split data to train (70%) test (30%),
    x_train, x_test, y_train, y_test = train_test_split(
        X, Y, test_size=0.3)  # train 70% and test=30%

    # train and predict
   # Create the parameter grid based on the results of random search
    param_grid = {
        'bootstrap': [True],
        'max_depth': [None],
        'max_features': [None],
        'min_samples_leaf': [1, 3, 4, 5],
        'min_samples_split': [2, 8, 10, 12],
        'n_estimators': [100, 200, 300, 1000]
    }
    # Create a based model
    rfr = RandomForestRegressor()
    # Instantiate the grid search model
    grid_search = GridSearchCV(estimator=rfr, param_grid=param_grid,
                               cv=5, n_jobs=-1)
    grid_search.fit(x_train, y_train)
    # grid_search.predict(x_val)

    # print results
    print("\n-----------------Results from Grid Search-----------------")
    print("\n The best estimator across ALL searched params:\n",
          grid_search.best_estimator_)
    print("\n The best score across ALL searched params:\n",
          grid_search.best_score_)
    print("\n The best parameters across ALL searched params:\n",
          grid_search.best_params_)


def grid_lasso_Regression(X, Y):
    print("\nTraining Lasso Regression...")
    # split data to train (70%) test (30%),
    x_train, x_test, y_train, y_test = train_test_split(
        X, Y, test_size=0.3)  # train 70% and test=30%

    # Fit regression model
    # It will check from 1e-08 to 1e+08
    alpha_sp = np.logspace(-8, 16, num=30)
    print(alpha_sp)
    params = {'alpha': alpha_sp}
    for normalize in [True, False]:
        lasso = Lasso(normalize=normalize)
        lasso_model = GridSearchCV(lasso, params, cv=5, n_jobs=-1)
        lasso_model.fit(x_train, y_train)

    # print results
    print("\n----------------Results from Grid Search for normalize= ",
          normalize, " ----------------")
    print("\n The best estimator across ALL searched params:\n",
          lasso_model.best_estimator_)
    print("\n The best score across ALL searched params:\n",
          lasso_model.best_score_)
    print("\n The best parameters across ALL searched params:\n",
          lasso_model.best_params_)


def grid_decision_tree(X, Y):
    print("\nTraining Decission Tree Regression...")
    # split data to train (70%) test (30%),
    x_train, x_test, y_train, y_test = train_test_split(
        X, Y, test_size=0.3)  # train 70% and test=30%

    dtr = DecisionTreeRegressor()  # create object,
    param_grid = {"criterion": ['mse', "mae"],
                  "min_samples_split": [2, 5, 10, 20, 40],
                  "max_depth": [None],
                  "min_samples_leaf": [1, 20, 40, 100],
                  "max_leaf_nodes": [None, 5, 20, 100],
                  }

    grid_search = GridSearchCV(dtr, param_grid, cv=5, n_jobs=-1)
    grid_search.fit(x_train, y_train)
    # print results
    print("\n-----------------Results from Grid Search-----------------")
    print("\n The best estimator across ALL searched params:\n",
          grid_search.best_estimator_)
    print("\n The best score across ALL searched params:\n",
          grid_search.best_score_)
    print("\n The best parameters across ALL searched params:\n",
          grid_search.best_params_)


def grid_polynomial_reg(X, Y):
    print("\nTraining Polynomial Regression...")
    # split data to train (70%) test (30%),
    x_train, x_test, y_train, y_test = train_test_split(
        X, Y, test_size=0.3)  # train 70% and test=30%

    for degree in [2, 3, 4]:
        # for normalize in [True, False]:

        plr = PolynomialFeatures(degree=degree)
        x_poly_train = plr.fit_transform(x_train)
        plr = LinearRegression()

        parameters = {'fit_intercept': [
            True, False], 'copy_X': [True, False]}
        grid = GridSearchCV(plr, parameters, cv=5, n_jobs=-1)
        grid.fit(x_poly_train, y_train)

        print("\n----------Results from Grid Search for degree= ",
              degree, "-------")
        print("\n The best estimator across ALL searched params:\n",
              grid.best_estimator_)
        print("\n The best score across ALL searched params:\n",
              grid.best_score_)
        print("\n The best parameters across ALL searched params:\n",
              grid.best_params_)


def grid_gbr(X, Y):
    print("\nTraining GBR Regression...")
    # split data to train (70%) test (30%),
    x_train, x_test, y_train, y_test = train_test_split(
        X, Y, test_size=0.3)  # train 70% and test=30%

    x_test, x_val, y_test, y_val = train_test_split(
        x_test, y_test, test_size=0.5)  # test= 20% and validation =20%

    gb = GradientBoostingRegressor()

    parameters = {'learning_rate': [0.3],
                  'n_estimators': [380, 200],
                  'subsample': [1.0],
                  'criterion': ['friedman_mse'],
                  'min_samples_split': [20, 30],
                  'min_samples_leaf': [1],
                  'min_weight_fraction_leaf': [0.0],
                  'max_depth': [7],
                  'min_impurity_decrease': [0.0],
                  'init': [None],
                  'random_state': [0],
                  'max_features': [None],
                  'alpha': [0.9, 0.5],
                  'max_leaf_nodes': [None],
                  'warm_start': [False]
                  }
    grid = GridSearchCV(gb, parameters, cv=5, n_jobs=-1)

    grid.fit(x_train, y_train)
    print("\n The best estimator across ALL searched params:\n",
          grid.best_estimator_)
    print("\n The best score across ALL searched params:\n",
          grid.best_score_)
    print("\n The best parameters across ALL searched params:\n",
          grid.best_params_)


# @-------------------MAIN--------------------------
df = pd.read_csv(r'dataset_filtered.csv')
X = df.values[:, :-1]  # features

# keep range SFS: '1', '2', '3', '5', '6', '7', '9', '11', '19', '22', '25', '27', '28' <---
# keep range SBS: 1, 2, 3, 4, 5, 6, 9, 11, 13, 22, 23, 25, 27, 28

Y = df.values[:, -1]  # target/price


# @grid thing
grid_random_forest(X, Y)
grid_linear_reg(X, Y)
grid_lasso_Regression(X, Y)
grid_decision_tree(X, Y)
grid_polynomial_reg(X, Y)
grid_support_vector_regression(X, Y)
grid_gbr(X, Y)
