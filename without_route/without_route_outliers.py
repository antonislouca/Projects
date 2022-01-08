
import pandas as pd
import matplotlib.pyplot as plt
from math import sqrt
from datetime import datetime, timedelta
import seaborn as sns
import scipy as sp

"""
function that performs outlier fildtering using the IQR method on the given column
a plot with the records in dataframe before and after the filtering is produced.
"""


def outlier_IQR(df, column_name):
    print('Removing outliers from column: ', column_name, ' using IQR')
    # find outliers
    Q1 = df[column_name].quantile(0.25)
    Q3 = df[column_name].quantile(0.75)
    IQR = Q3-Q1
    # finding lower and upper limit
    maximum = Q3 + 1.5*IQR
    minimum = Q1 - 1.5*IQR
    new_df = df[(df[column_name] > minimum) &
                (df[column_name] < maximum)]

    # PLOTS AFTER TRIMMING
    plt.figure(figsize=(16, 8))
    plt.subplot(2, 2, 1)
    sns.histplot(data=df[column_name], x=df[column_name], kde=True)
    plt.title('Before IQR method ' + str(column_name))
    plt.subplot(2, 2, 2)
    sns.boxplot(data=df[column_name], x=df[column_name])
    plt.title('Before IQR method ' + str(column_name))
    plt.subplot(2, 2, 3)
    sns.histplot(data=new_df[column_name], x=new_df[column_name], kde=True)
    plt.title('After IQR method ' + str(column_name))
    plt.subplot(2, 2, 4)
    sns.boxplot(data=new_df[column_name], x=new_df[column_name])
    plt.title('After IQR method ' + str(column_name))
    plt.show()
    print(new_df)

    return new_df


"""
function that performs outlier fildtering using the MAD method on the given column
a plot with the records in dataframe before and after the filtering is produced.
"""


def outliers_MAD(df, column_name):
    print('Removing outliers from column: ', column_name, ' using MAD')
    median = df[column_name].median()
    mad = sp.stats.median_abs_deviation(df[column_name])
    maximum = median + 3*mad
    minimum = median - 3*mad

    new_df = df[(df[column_name] > minimum) &
                (df[column_name] < maximum)]

    # PLOTS AFTER TRIMMING
    plt.figure(figsize=(16, 8))
    plt.subplot(2, 2, 1)
    sns.histplot(data=df[column_name], x=df[column_name], kde=True)
    plt.title('Before MAD method ' + str(column_name))
    plt.subplot(2, 2, 2)
    sns.boxplot(data=df[column_name], x=df[column_name])
    plt.title('Before MAD method ' + str(column_name))
    plt.subplot(2, 2, 3)
    sns.histplot(data=new_df[column_name], x=new_df[column_name], kde=True)
    plt.title('After MAD method ' + str(column_name))
    plt.subplot(2, 2, 4)
    sns.boxplot(data=new_df[column_name], x=new_df[column_name])
    plt.title('After MAD method ' + str(column_name))
    plt.show()
    print(new_df)
    print('Done')


# ---------------------------------------------------
#!only chage made is the name of I/O files
df = pd.read_csv(r'dataset_after_preprocessing.csv')
X = df.values[:, : -1]  # features
Y = df.values[:, -1]  # target


# outlier detection for column 'Total_Stops'
uniqueClass = df.groupby('Total_Stops').size()
for key in uniqueClass.keys():
    print('-> stop', key, 'appears', uniqueClass[key], 'times.')

outliers_MAD(df, 'Price')  # just run does not modify frame
df = outlier_IQR(df, str('Price'))  # modifies df


outliers_MAD(df, 'Total_Stops')  # just run does not modify frame
df = outlier_IQR(df, str('Total_Stops'))  # modifies df

outliers_MAD(df, 'Duration')  # just run does not modify frame
df = outlier_IQR(df, str('Duration'))  # modifies df


df = df.drop('Route', 1)
df = df.drop('Arrival_Time', 1)
# df['Arrival_Time'] = df['Arrival_Time'].str.split(' ').str[0]
df.to_csv(r'dataset_filtered.csv', index=False)
