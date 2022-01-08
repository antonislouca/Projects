import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from sklearn.ensemble import ExtraTreesClassifier
from sklearn.feature_selection import RFE
import seaborn as sns
from matplotlib import cm as cm
import collections
from mlxtend.feature_selection import SequentialFeatureSelector as SFS
from sklearn.linear_model import Lasso
from sklearn.linear_model import LinearRegression
from mlxtend.plotting import plot_sequential_feature_selection as plot_sfs

df = pd.read_csv(r'dataset_filtered.csv')
# df = df.drop(columns=['Airl_Jet Airways Business'])
array = df.values

# feature importance 1st attempt: using ensemble algorithm (estimator = forest of trees)
# ***limited number of rows !!

"""
this function uses ensemmble regression and uses extra trees classifier. The goal is to 
create a descending order of features based on the importance of the given feature. Meaning that 
features we see first, are closes to the given feature and have higher importance to it.
"""


def first_feauture_importance(input):
    df_sub = df.copy()
    X = array[:, 0:32]   # features
    y = array[:, 31]     # target - column "price"
    # if input == 0:
    #     X = array[:, 0:32]   # features
    #     y = array[:, 32]     # target - column "price"
    # else:
    #     df_sub = df[['Duration', 'Total_Stops', 'Additional_Info', 'day_sin',
    #                  'day_cos', 'month_sin', 'month_cos', 'Dep_hour_sin', 'Dep_hour_cos', 'Price']]
    #     X = array[:, 0:9]   # features
    #     y = array[:, 9]     # target - column "price"

    estimator = ExtraTreesClassifier(
        n_estimators=100, max_features=32, random_state=0)
    estimator.fit(X, y)
    importances = estimator.feature_importances_
    std = np.std(
        [tree.feature_importances_ for tree in estimator.estimators_], axis=0)
    indices = np.argsort(importances)[::-1]

    print("Feature ranking:")
    for f in range(X.shape[1]):
        print("%d. feature %d - column name = '%s' (%f)" %
              (f + 1, indices[f], df.columns[indices[f]], importances[indices[f]]))

    plt.figure()
    plt.title("Feature importances")
    plt.bar(range(X.shape[1]), importances[indices],
            color="r", yerr=std[indices], align="center")
    plt.xticks(range(X.shape[1]), indices)
    plt.xlim([-1, X.shape[1]])
    plt.show()


# feature importance 2nd attempt: using RFE (estimator = forest of trees)
# ***limited number of rows !!
"""
performs same process as first feature importance function but uses recursive feature elimination
"""


def second_feauture_importance(input):
    df_sub = df.copy()
    X = array[:, 0:32]   # features
    y = array[:, 32]     # target - column "price"

    # if input == 0:
    #     X = array[:, 0:32]   # features
    #     y = array[:, 32]     # target - column "price"
    # # else:
    #     df_sub = df[['Duration', 'Total_Stops', 'Additional_Info', 'day_sin',
    #                  'day_cos', 'month_sin', 'month_cos', 'Dep_hour_sin', 'Dep_hour_cos', 'Price']]
    #     X = array[:, 0:9]   # features
    #     y = array[:, 9]     # target - column "price"

    n = 5
    estimator = ExtraTreesClassifier(n_estimators=100, random_state=0)
    selector = RFE(estimator, n_features_to_select=n, step=1)
    selector = selector.fit(X, y)
    # print(list(selector.support_))
    # print(list(selector.ranking_))

    columns_ranking = dict()
    for i in range(len(df.columns)-1):
        key = df.columns[i]
        value = selector.ranking_[i]
        columns_ranking[key] = value

    ranking_columns = dict()

    def append_value(dict_obj, key, value):
        if key in dict_obj:
            if not isinstance(dict_obj[key], list):
                dict_obj[key] = [dict_obj[key]]
            dict_obj[key].append(value)
        else:
            dict_obj[key] = [value]

    for k, v in columns_ranking.items():
        append_value(ranking_columns, v, k)

    # ranking_columns_ordered = collections.OrderedDict(sorted(ranking_columns.items()))
    print('feature importance with second approach (RFE)')
    print('Number of features selected:', n)
    print('Most important columns are (key=1):', ranking_columns[1])
    print('Also important columns are (key=2,3):',
          ranking_columns[2], 'and', ranking_columns[3])
    print('--------------------------------------------------------------------------------------')


"""creates a correlation map that shows the relation between different features"""

# feature correlation (heatmap)


def feature_corr():
    X = array[:, :]  # features + target
    plt.figure(figsize=(100, 100))
    corr = pd.DataFrame(X).corr(method='pearson')
    cmap = cm.get_cmap('jet', 50)
    # _, ax = plt.subplots()
    sns.heatmap(corr, cmap=cmap,  annot=True, annot_kws={'fontsize': 6},
                xticklabels=corr.columns, yticklabels=corr.columns)
    plt.savefig(r'heatmapFeatures.png')
    plt.show()
    # high correlation: 0-1 [Duration-Total_Stops], 24-16, 21-17, 22-15, 18-20, 19-23 [routes]
    # important: 0-32 [Duration-Price] = 0.577, 1-32 [Total_Stops-Price] = 0.670


# feature selection
# regression algorithms: ElasticNet Lasso, RidgeRegression SVR(kernel='linear'),        BACKUP algo:SVR(kernel='rbf')EnsembleRegressions
# regression problems: Mean Absolute Error, Mean Squared Error
"""
a function that uses sfs process once using k variable to be the number of features 
that we want to retrieve
"""


def feature_sel_sfs():
    X = array[:, 0:32]   # features
    y = array[:, 31]     # target - column "price"
    k = 10
    alpha = 0.9         # when alpha=0 => Lasso = Linear Regression
    lasso = Lasso(alpha=alpha)

    # Sequential Forward Selection
    # scoring parameter: neg_mean_absolute_error or neg_mean_squared_error (link: https://scikit-learn.org/0.24/modules/model_evaluation.html#scoring-parameter)
    sfs = SFS(lasso,
              k_features=k,
              forward=True,
              floating=False,
              verbose=2,
              scoring='neg_mean_squared_error',  # neg_mean_absolute_error
              cv=10)

    sfs = sfs.fit(X, y)

    print('\nSequential Forward Selection for k =', k)
    print('Selected features:', sfs.k_feature_idx_)
    print('Prediction score:', sfs.k_score_)
    print('-----------------------------------------------------------')

# feature selection


"""
a function that uses sbs process once using k variable to be the number of features 
that we want to retrieve
"""


def feature_sel_sbs():
    X = array[:, :-1]   # features
    y = array[:, -1]     # target - column "price"
    k = 10
    lr = LinearRegression()

    sbs = SFS(lr,
              k_features=(1, 31),
              forward=False,
              floating=False,
              scoring='neg_mean_squared_error',
              cv=10)

    sbs = sbs.fit(X, y)
    fig = plot_sfs(sbs.get_metric_dict(), kind='std_err')

    plt.title('Sequential Backward Elimination (w. StdErr)')
    plt.grid()
    plt.show()

    print('\nSequential Backward Selection k =', k)
    print('Selected features:', sbs.k_feature_idx_)
    print('Prediction (CV) score:', sbs.k_score_)


"""
function that performs the range process for SBS  backwawrd feature selection
uses a range of features from 1 to 31
"""


def selection_with_range_SBS():
    X = array[:, :-1]  # features
    y = array[:, -1]  # target - column "price"
    k = 10
    # alpha = 0  # when alpha=0 => Lasso = Linear Regression
    # lasso = Lasso(alpha=alpha)
    lr = LinearRegression()
    #!this previously had lasso estimator
    sfs_range = SFS(estimator=lr,
                    k_features=(1, 31),
                    forward=False,
                    floating=False,
                    scoring='neg_mean_squared_error',
                    cv=10)

    sfs_range = sfs_range.fit(X, y)

    print('best combination (score: %.3f): %s\n' %
          (sfs_range.k_score_, sfs_range.k_feature_idx_))
    print('all subsets:\n', sfs_range.subsets_)
    plot_sfs(sfs_range.get_metric_dict(), kind='std_err')
    plt.show()


"""
function that performs the range process for sfs  forward feature selection
uses a range of features from 1 to 31
"""


def selection_with_range_SFS():
    X = array[:, :-1]  # features
    y = array[:, -1]  # target - column "price"
    k = 10
    # alpha = 0  # when alpha=0 => Lasso = Linear Regression
    # lasso = Lasso(alpha=alpha)
    lr = LinearRegression()
    #!this previously had lasso estimator
    sfs_range = SFS(estimator=lr,
                    k_features=(1, 31),
                    forward=True,
                    floating=False,
                    scoring='neg_mean_squared_error',
                    cv=10)

    sfs_range = sfs_range.fit(X, y)

    print('best combination (score: %.3f): %s\n' %
          (sfs_range.k_score_, sfs_range.k_feature_idx_))
    print('all subsets:\n', sfs_range.subsets_)
    plot_sfs(sfs_range.get_metric_dict(), kind='std_err')
    plt.show()


# @ MAIN
# first_feauture_importance(1)


feature_corr()
# feature_sel_sfs()
# feature_sel_sbs()

print("selection_with_range_SBS...")
selection_with_range_SBS()
print("\nselection_with_range_SFS...")
selection_with_range_SFS()  # repetition of SFS
