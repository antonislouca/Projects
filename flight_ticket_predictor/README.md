# Epl448_group_project - Flight ticket prediction
 
Instructions for how to use the Project 

Version with route feature
===========================
All the needed files to run the training and the predictions of the chosen algorithms with the best 
parameters are already in the project.

If you want to run the procedure from the beginning (preprocessing of given dataset, finding outliers, feature
selection and grid search) then you have to follow the next steps:
1. Run the route_preprocessing.py  produces dataset_after_preprocessing.csv
2. Run the route_outliers.py produces dataset_filtered.csv
3. Run the route_featureSelection.py runs with dataset_filtered.csv
4. (you can skip this part - it takes some time to execute) Run route_gridsearch.py
5. If you want to change the parameters of each algorithm you have to edit the code in route_predictions and then
run the route_predictions.py (it also makes the training of the algorithms)

If you want to run the algorithms with the given datasets just run the route_predictions.py file

Version without route feature
===========================
All the needed files to run the training and the predictions of the chosen algorithms with the best 
parameters are already in the project.

If you want to run the procedure from the beginning (preprocessing of given dataset, finding outliers, feature
selection and grid search) then you have to follow the next steps:
1. Run the without_route_preprocessing.py produces dataset_after_preprocessing.csv
2. Run the without_route_outliers.py produces dataset_filtered.csv
3. Run the without_route_featureSelection.py runs with dataset_filtered.csv
4. (you can skip this part - it takes some time to execute) Run without_route_gridsearch.py
5. If you want to change the parameters of each algorithm you have to edit the code in without_route_predictions.py and then
run the without_route_predictions.py (it also makes the training of the algorithms)

If you want to run the algorithms with the given datasets just run the without_route_gridsearch.py file
The prediction file runs on dataset_filtered.csv to make predictions